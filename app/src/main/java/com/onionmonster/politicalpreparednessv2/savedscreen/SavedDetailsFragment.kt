package com.onionmonster.politicalpreparednessv2.savedscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.Person.fromBundle
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.databinding.FragmentSavedDetailsBinding
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingDetailsFragmentArgs
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingDetailsViewModel
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingDetailsViewModelFactory


class SavedDetailsFragment : Fragment() {
    val TAG = "Dev/" + javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentSavedDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val election = SavedDetailsFragmentArgs.fromBundle(requireArguments()).selectedElection
        val viewModelFactory = SavedDetailsViewModelFactory(election, application)

        binding.election = election
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SavedDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.electionSaveStatus.observe(viewLifecycleOwner, { saveStatus ->
            Log.d(TAG, saveStatus.toString())

            binding.savedFollowElectionButton.text = if (saveStatus == 0) "Follow Election" else "Unfollow Election"

            binding.savedFollowElectionButton.setOnClickListener {
                if (saveStatus == 0) {
                    viewModel.selectedElection.value?.let { it1 -> viewModel.setElectionSaveStatus(it1, 1) }
                } else {
                    viewModel.selectedElection.value?.let { it1 -> viewModel.setElectionSaveStatus(it1, 0) }
                }
            }
        })

        viewModel.electionDetails.observe(viewLifecycleOwner, { eDetails ->
            binding.savedVotingLocation.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
//                browserIntent.data = Uri.parse(eDetails.votingLocation)
                browserIntent.data = Uri.parse("https://www.google.com")
                startActivity(browserIntent)
            }

            binding.savedVotingLocation.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://www.bing.com")
//                browserIntent.data = Uri.parse(eDetails.ballotInfo)
                startActivity(browserIntent)
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }
}