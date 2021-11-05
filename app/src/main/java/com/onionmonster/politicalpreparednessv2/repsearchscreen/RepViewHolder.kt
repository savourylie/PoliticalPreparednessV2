package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.onionmonster.politicalpreparednessv2.R
import com.onionmonster.politicalpreparednessv2.data.Representative


class RepViewHolder private constructor (itemView: View): RecyclerView.ViewHolder(itemView) {

    private val context: Context = itemView.context

    private val container: CardView = itemView.findViewById(R.id.rep_search_result_item)
    private val role: TextView = itemView.findViewById(R.id.rep_role)
    private val name: TextView = itemView.findViewById(R.id.rep_name)
    private val party: TextView = itemView.findViewById(R.id.rep_party)
    private val webSiteIcon: ImageView = itemView.findViewById(R.id.webSiteIcon)
    private val facebookIcon: ImageView = itemView.findViewById(R.id.facebookIcon)
    private val twitterIcon: ImageView = itemView.findViewById(R.id.twitterIcon)


    fun bind(rep: Representative) {
        role.text = rep.role
        name.text = rep.name
        party.text = rep.party

        webSiteIcon.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(rep.webSiteUrl)
            context.startActivity(browserIntent)
        }

        facebookIcon.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(rep.facebookUrl)
            context.startActivity(browserIntent)
        }

        twitterIcon.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(rep.twitterUrl)
            context.startActivity(browserIntent)
        }
    }

    companion object {
        fun from(parent: ViewGroup,
        ): RepViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.rep_search_result_item, parent, false) as CardView

            return RepViewHolder(view)
        }
    }
}