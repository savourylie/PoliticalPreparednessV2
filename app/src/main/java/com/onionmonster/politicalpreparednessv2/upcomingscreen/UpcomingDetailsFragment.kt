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

class UpcomingDetailsFragment : Fragment() {
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
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(UpcomingDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_upcoming_details, container, false)
    }
}