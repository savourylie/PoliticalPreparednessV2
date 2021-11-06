package com.onionmonster.politicalpreparednessv2.savedscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.databinding.FragmentSavedBinding
import com.onionmonster.politicalpreparednessv2.upcomingscreen.ElectionAdapter
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingFragmentDirections
import com.onionmonster.politicalpreparednessv2.upcomingscreen.UpcomingViewModel

class SavedFragment : Fragment() {

    val TAG = "Dev/" + javaClass.simpleName

    lateinit var binding: FragmentSavedBinding
    private lateinit var electionAdapter: ElectionAdapter
    private val viewModel: SavedViewModel by lazy {
        ViewModelProvider(this).get(SavedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        electionAdapter = ElectionAdapter(viewModel)

        binding.recyclerSavedElection.apply {
            setHasFixedSize(true)
            adapter = electionAdapter
        }

        viewModel.electionList.observe(viewLifecycleOwner) { electionsSaved ->
            electionsSaved?.apply {
                electionAdapter.submitList(electionsSaved)
            }
        }

        viewModel.navigateToElectionDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController().navigate(
                    SavedFragmentDirections.actionSavedToDetails(it)
                )

                viewModel.onElectionDetailNavigated()
            }
        })

        return binding.root
    }
}