package com.onionmonster.politicalpreparednessv2.mylocsearchscreen

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.snackbar.Snackbar
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.databinding.FragmentMyLocationSearchBinding
import com.onionmonster.politicalpreparednessv2.foregroundLocationPermissionApproved
import com.onionmonster.politicalpreparednessv2.repsearchscreen.RepAdapter
import com.onionmonster.politicalpreparednessv2.requestForegroundPermission

import android.location.Geocoder
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.onionmonster.politicalpreparednessv2.data.Address
import kotlinx.coroutines.launch


class MyLocationSearchFragment : Fragment() {

    val TAG = javaClass.simpleName

    lateinit var binding: FragmentMyLocationSearchBinding
    private lateinit var viewModel: MyLocationSearchViewModel
    private lateinit var repAdapter: RepAdapter

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            checkDeviceLocationSettingsAndGetAddress()
        }
    }

    lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_my_location_search,
                                          container, false)

        binding.lifecycleOwner = this
        val viewModelFactory = MyLocationSearchViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MyLocationSearchViewModel::class.java)

        binding.viewModel = viewModel

        repAdapter = RepAdapter(viewModel)

        binding.recyclerMylocResult.apply {
            setHasFixedSize(true)
            adapter = repAdapter
        }

        viewModel.repList.observe(viewLifecycleOwner) {
            it?.apply {
                Log.d(TAG, "repList submitted.")
                Log.d(TAG, it.toString())
                repAdapter.submitList(it)
            }
        }

        checkDeviceLocationSettingsAndGetAddress()

        viewModel.currentAddress.observe(viewLifecycleOwner) {
            binding.mylocTitle.mylocTitleText.text = it
            viewModel.viewModelScope.launch {
                viewModel.repRepository.refreshReps(Address(it))
            }
        }

        return binding.root
    }

    private fun checkDeviceLocationSettingsAndGetAddress(resolve:Boolean = true) {

        Log.d(TAG, "checkDeviceLocationSettingsAndGetAddress() called.")

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val locationSettingRequestsBuilder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(locationSettingRequestsBuilder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->

            if (exception is ResolvableApiException && resolve) {
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    resultLauncher.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error getting location settings resolution: " + sendEx.message)
                }
            } else {
                Snackbar.make(
                    this.requireView(),
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndGetAddress()
                }.show()
            }
        }

        locationSettingsResponseTask.addOnCompleteListener {
            if ( it.isSuccessful ) {
                Log.d(TAG, "Device location enabled")
                enableMyLocationAndGetMyAddress()
            }
        }
    }

    private fun enableMyLocationAndGetMyAddress() {
        Log.d(TAG, "enableMyLocationAndGetMyAddress() called.")

        if (!foregroundLocationPermissionApproved(requireContext())) {

            requestForegroundPermission(requireContext(), ::requestPermissions)
        } else {

            Log.d(TAG, "Foreground permission granted.")

            val gCoder = Geocoder(requireContext())

            viewModel.getCurrentAddress(gCoder, fusedLocationClient)

        }
    }

}