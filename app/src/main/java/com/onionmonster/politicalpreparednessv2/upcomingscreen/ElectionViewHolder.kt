package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.data.Election


class ElectionViewHolder private constructor (itemView: View,
                                              private val electionListener: OnElectionSelectedListener,
                                             ): RecyclerView.ViewHolder(itemView) {

    private val container: CardView = itemView.findViewById(R.id.election_item)
    private val eventTitle: TextView = itemView.findViewById(R.id.election_title)
    private val eventDateTime: TextView = itemView.findViewById(R.id.election_datetime)

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(event: Election) {
        eventTitle.text = event.title
        eventDateTime.text = event.datetime

        container.setOnClickListener {
            electionListener.onElectionClicked(event)
        }

//        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup,
                 electionListener: OnElectionSelectedListener,
                 ): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.election_item, parent, false) as CardView

            return ElectionViewHolder(view, electionListener)
        }
    }
}