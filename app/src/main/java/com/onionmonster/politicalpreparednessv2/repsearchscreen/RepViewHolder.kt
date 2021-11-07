package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.onionmonster.politicalpreparednessv2.data.ProfilePic
import com.onionmonster.politicalpreparednessv2.data.Representative
import com.onionmonster.politicalpreparednessv2.databinding.RepSearchResultItemBinding
import com.onionmonster.politicalpreparednessv2.network.asDomainModel
import com.onionmonster.politicalpreparednessv2.network.getProfileObject
import kotlinx.coroutines.launch


class RepViewHolder private constructor (private var binding: RepSearchResultItemBinding): RecyclerView.ViewHolder(binding.root) {
    private val TAG = javaClass.simpleName

    private val context: Context = binding.root.context

    fun bind(rep: Representative, viewModel: AndroidViewModel) {
        binding.repRole.text = rep.role
        binding.repName.text = rep.name
        binding.repParty.text = rep.party

        binding.webSiteIcon.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)

            rep.webSiteUrl.apply {
                browserIntent.data = Uri.parse(if (this.isEmpty()) "https://www.google.com" else this)
            }
            context.startActivity(browserIntent)
        }

        binding.facebookIcon.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            rep.facebookUrl.apply {
                browserIntent.data = Uri.parse(if (this.isEmpty()) "https://www.google.com" else this)
            }
            context.startActivity(browserIntent)
        }

        binding.twitterIcon.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            rep.twitterUrl.apply {
                browserIntent.data = Uri.parse(if (this.isEmpty()) "https://www.google.com" else this)
            }
            context.startActivity(browserIntent)
        }

        viewModel.viewModelScope.launch {
            Log.d(TAG, "TwitterUrl: " + rep.twitterUrl)

            val twitterAccountId = parseTwitterUrlForId(rep.twitterUrl)

            Log.d(TAG, "TwitterAccountId: " + twitterAccountId)

            val profilePicProperty = getProfileObject(twitterAccountId)
            val profilePic = profilePicProperty?.asDomainModel()
                ?: ProfilePic(name = rep.name, username = "", profilePicUrl = "")

            binding.profilePic = profilePic
        }

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup,
        ): RepViewHolder {
            val binding = RepSearchResultItemBinding.inflate(LayoutInflater.from(parent.context))
            return RepViewHolder(binding)
        }
    }
}

fun parseTwitterUrlForId(twitterUrl: String): String {
    return if (!twitterUrl.contains("/") || twitterUrl.last() == '/') {
        ""
    } else {
        twitterUrl.split("/").last()
    }
}