package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.databinding.FragmentRepSearchBinding
import com.onionmonster.politicalpreparednessv2.databinding.FragmentRepSearchBindingImpl
import android.widget.ArrayAdapter

import android.R

import android.widget.Spinner




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
//        val states = arrayOf("Arizona", "California", "Delaware")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, states)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        return binding.root
    }
}