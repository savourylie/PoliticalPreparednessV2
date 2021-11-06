package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.databinding.FragmentRepSearchResultBinding
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingDetailsViewModel
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingDetailsViewModelFactory
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingViewModel


class RepSearchResultFragment : Fragment() {

    val TAG = javaClass.simpleName

    lateinit var binding: FragmentRepSearchResultBinding
    private lateinit var viewModel: RepSearchResultViewModel
    private lateinit var repAdapter: RepAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_rep_search_result,
                                          container, false)

        binding.lifecycleOwner = this

        val address = RepSearchResultFragmentArgs.fromBundle(requireArguments()).myAddress
        val viewModelFactory = RepSearchResultViewModelFactory(address, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RepSearchResultViewModel::class.java)

        binding.viewModel = viewModel

        repAdapter = RepAdapter()

        binding.recyclerRepSearchResult.apply {
            setHasFixedSize(true)
            adapter = repAdapter
        }

        return binding.root
    }

}