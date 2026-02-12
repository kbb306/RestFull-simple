package com.example.restfullsimple
import android.app.Application
import androidx.lifecycle.ViewModel
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.map
import kotlin.collections.map

class RestFullViewModel(application: Application) : AndroidViewModel(application) {
    val model = Percent("Default",100)
    val batt = BatteryListener(application.applicationContext)

    fun percent(percent : Int) {
        model.threshold = percent
    }

}