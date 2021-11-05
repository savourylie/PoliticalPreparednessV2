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
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingViewModel


class RepSearchResultFragment : Fragment() {

    val TAG = javaClass.simpleName

    lateinit var binding: FragmentRepSearchResultBinding
    private val viewModel: RepSearchResultViewModel by lazy {
        ViewModelProvider(this).get(RepSearchResultViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_rep_search_result,
                                          container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerRepSearchResult.apply {
            setHasFixedSize(true)
        }

        return binding.root
    }

}