package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.databinding.FragmentUpcomingBinding
import com.onionmonster.politicalpreparednessv2.databinding.FragmentUpcomingDetailsBinding
import android.content.Intent
import android.net.Uri
import android.util.Log


class UpcomingDetailsFragment : Fragment() {
    val TAG = "Dev/" + javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(activity).application
        val binding = FragmentUpcomingDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val election = UpcomingDetailsFragmentArgs.fromBundle(arguments!!).selectedElection
        val viewModelFactory = UpcomingDetailsViewModelFactory(election, application)

        binding.election = election

        val viewModel = ViewModelProvider(this, viewModelFactory).get(UpcomingDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.electionSaveStatus.observe(viewLifecycleOwner, { saveStatus ->
            Log.d(TAG, saveStatus.toString())

            binding.followElectionButton.text = if (saveStatus == 0) "Follow Election" else "Unfollow Election"

            binding.followElectionButton.setOnClickListener {
                if (saveStatus == 0) {
                    viewModel.selectedElection.value?.let { it1 -> viewModel.setElectionSaveStatus(it1, 1) }
                } else {
                    viewModel.selectedElection.value?.let { it1 -> viewModel.setElectionSaveStatus(it1, 0) }
                }
            }
        })

        viewModel.electionDetails.observe(viewLifecycleOwner, { eDetails ->
            binding.votingLocation.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
//                browserIntent.data = Uri.parse(eDetails.votingLocation)
                browserIntent.data = Uri.parse("https://www.google.com")
                startActivity(browserIntent)
            }

            binding.ballotInfo.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://www.bing.com")
//                browserIntent.data = Uri.parse(eDetails.ballotInfo)
                startActivity(browserIntent)
            }
        })



        return binding.root
    }
}