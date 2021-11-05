package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    val TAG = "Dev/" + javaClass.simpleName

    lateinit var binding: FragmentUpcomingBinding
    private lateinit var electionAdapter: ElectionAdapter

    private val viewModel: UpcomingViewModel by lazy {
        ViewModelProvider(this).get(UpcomingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        electionAdapter = ElectionAdapter(viewModel)

        binding.recyclerUpcomingElection.apply {
            setHasFixedSize(true)
            adapter = electionAdapter
        }

        viewModel.navigateToElectionDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController().navigate(
                    UpcomingFragmentDirections.actionUpcomingToDetails(it)
                )

                viewModel.onElectionDetailNavigated()
            }
        })

        viewModel.electionList.observe(viewLifecycleOwner, { elections ->
            elections?.apply {
                electionAdapter.submitList(elections)
            }
        })

        return binding.root
    }


}