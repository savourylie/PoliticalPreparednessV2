package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import com.onionmonster.politicalpreparednessv2.data.Representative

class RepAdapter(val viewModel: AndroidViewModel): ListAdapter<Representative, RepViewHolder>(RepDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepViewHolder {
        return RepViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepViewHolder, position: Int) {
        val rep = getItem(position)

        holder.bind(rep, viewModel)
    }
}

class RepDiffCallback: DiffUtil.ItemCallback<Representative>() {
    override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem.role == newItem.role && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem == newItem
    }
}