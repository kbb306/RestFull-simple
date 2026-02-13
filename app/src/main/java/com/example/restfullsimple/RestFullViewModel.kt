package com.example.restfullsimple
import android.app.Application
import androidx.lifecycle.ViewModel
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.round
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.map
import java.util.Calendar
import kotlin.collections.map

class RestFullViewModel(application: Application) : AndroidViewModel(application) {
    val model = Percent("Default",100)
    val batt = BatteryListener(application.applicationContext)

    fun percent(percent : Int) {
        model.threshold = percent
    }
    fun name(name : String) {
        model.name = name
    }

    fun display(): String {
        val string = when {
            model.threshold == null -> "Err"
            else -> model.threshold.toString()
        }
        return string
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun output() : String{
        val time = batt.getTime(model)
        val hour =  when {
            time == null -> "Nan"
            else -> time.div(60).toString()
        }
        val minute = when {
            time == null -> "NaN"
            else -> (time.rem(60)).times(60).toString()
        }
        return "Set a timer for $hour:$minute"

    }

}