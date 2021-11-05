package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.databinding.FragmentRepSearchBinding
import com.onionmonster.politicalpreparednessv2.databinding.FragmentRepSearchBindingImpl
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.data.Address


class RepSearchFragment : Fragment() {

    val TAG = "Dev/" + javaClass.simpleName
    lateinit var binding: FragmentRepSearchBinding

    private val viewModel: RepSearchViewModel by lazy {
        ViewModelProvider(this).get(RepSearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentRepSearchBindingImpl.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val states = resources.getStringArray(R.array.states)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, states)
        binding.stateValue.setAdapter(arrayAdapter)

        binding.searchRepButton.setOnClickListener {
            binding.apply {
                if (addressLine1.text.toString() == "") {
                    Toast.makeText(context, "Address line 1 cannot be empty!", Toast.LENGTH_SHORT).show()
                } else if (city.text.toString() == "") {
                    Toast.makeText(context, "City cannot be empty!", Toast.LENGTH_SHORT).show()
                } else if (stateValue.text.toString() == "") {
                    Toast.makeText(context, "State not selected!", Toast.LENGTH_SHORT).show()
                } else {
                    val myAddress = Address(
                            addressLine1 = addressLine1.text.toString(),
                            addressLine2 = addressLine2.text.toString(),
                            city = city.text.toString(),
                            state = stateValue.text.toString()
                    )

                    viewModel!!.onSearchClicked(myAddress)
                }
            }
        }

        viewModel.navigateToRepList.observe(viewLifecycleOwner, {
            if (it != null) {
                Log.d(TAG, "navigateToRepList: " + it.toString())

                findNavController().navigate(
                    RepSearchFragmentDirections.actionRepsearchToResult(it)
                )

                viewModel.onRepListNavigated()
            }
        })

        return binding.root
    }
}