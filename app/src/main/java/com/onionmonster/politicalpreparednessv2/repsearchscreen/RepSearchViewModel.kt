package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onionmonster.politicalpreparednessv2.data.Address
import com.onionmonster.politicalpreparednessv2.data.Election

class RepSearchViewModel: ViewModel() {

    val TAG = javaClass.simpleName

    val addressLine1 = MutableLiveData<String>()
    val addressLine2 = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()

    private val _navigateToRepList = MutableLiveData<Address>()
    val navigateToRepList: LiveData<Address>
        get() = _navigateToRepList

//    fun fillAddressWithData(): Address {
//        val myAddress = Address(
//                addressLine1 = addressLine1.value!!,
//                addressLine2 = addressLine2.value!!,
//                city = city.value!!,
//                state = state.value!!,
//        )
//
//        return myAddress
//    }

    fun onSearchClicked(address: Address) {

        Log.d(TAG, "onSearchClicked!")
        _navigateToRepList.value = address
    }

    fun onRepListNavigated() {
        _navigateToRepList.value = null
    }

}