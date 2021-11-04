package com.onionmonster.politicalpreparednessv2

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("electionIdText")
fun bindTextViewToElectionId(textView: TextView, id: String) {
    val context = textView.context
    textView.text = id
}