package com.onionmonster.politicalpreparednessv2.network

import com.onionmonster.politicalpreparednessv2.database.DatabaseRepresentative

data class RepProperty(
    val contests: List<Contest>
)

data class Contest(
    val roles: List<String>?,
    val candidates: List<Candidate>?
)

data class Candidate(
    val name: String,
    val party: String,
    val candidateUrl: String?,
    val channels: List<Channel>?
)

data class Channel(
    val type: String,
    val id: String
)

fun RepProperty.asDatabaseModel(): Array<DatabaseRepresentative> {
    val repArrayList = ArrayList<DatabaseRepresentative>()

    contests.forEach { contest ->

        if (contest.candidates != null) {
            contest.candidates.forEach { candidate ->
                var webSiteUrl = ""
                var facebookUrl = ""
                var twitterUrl = ""

                if (contest.roles != null) {
                    candidate.channels?.forEach { channel ->
                        when (channel.type) {
                            "Facebook" -> {facebookUrl = channel.id}
                            "Twitter" -> {twitterUrl = channel.id}
                        }
                    }

                    repArrayList.add(DatabaseRepresentative(
                        id = null,
                        role = contest.roles[0],
                        name = candidate.name,
                        party = candidate.party,
                        webSiteUrl = candidate.candidateUrl,
                        facebookUrl = facebookUrl,
                        twitterUrl = twitterUrl
                    ))
                }

            }
        }

    }

    return repArrayList.toTypedArray()

//    return contests.map { contest ->
//
//        contest.candidates.forEach { candidate ->
//            var webSiteUrl = ""
//            var facebookUrl = ""
//            var twitterUrl = ""
//
//            candidate.channels?.forEach { channel ->
//                when (channel.type) {
//                    "Facebook" -> {facebookUrl = channel.id}
//                    "Twitter" -> {twitterUrl = channel.id}
//                }
//            }
//
//            DatabaseRepresentative(
//                role = contest.roles[0],
//                name = candidate.name,
//                party = candidate.party,
//                webSiteUrl = candidate.candidateUrl,
//                facebookUrl = facebookUrl,
//                twitterUrl = twitterUrl
//            )
//        }
//    }
}