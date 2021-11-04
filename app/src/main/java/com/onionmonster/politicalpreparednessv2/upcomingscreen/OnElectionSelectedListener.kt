package com.onionmonster.politicalpreparednessv2.upcomingscreen

import com.onionmonster.politicalpreparednessv2.data.Election



interface OnElectionSelectedListener {
    fun onElectionClicked(election: Election)
}

interface OnSaveIconSelectedListener {
    fun onSaveIconClicked(election: Election)
}