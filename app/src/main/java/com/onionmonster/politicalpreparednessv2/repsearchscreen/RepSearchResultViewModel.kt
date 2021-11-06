package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.onionmonster.politicalpreparednessv2.data.Address
import com.onionmonster.politicalpreparednessv2.database.getDatabase
import com.onionmonster.politicalpreparednessv2.repository.ElectionRepository

class RepSearchResultViewModel(address: Address, application: Application):
    AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repRepository = ElectionRepository(database)

}