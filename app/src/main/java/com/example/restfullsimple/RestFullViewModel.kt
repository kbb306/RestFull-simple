package com.example.restfullsimple
import android.app.Application

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel


class RestFullViewModel(application: Application) : AndroidViewModel(application) {
    val model = Percent(100)
    val batt = BatteryListener(application.applicationContext)

    fun percent(percent : Int) {
        model.threshold = percent
    }

    fun getPer(): Int {
        return model.threshold
    }

    fun output() : String{
        val time = batt.getTime(model.threshold)
        return when {
            time == null -> "Can't get value (Are you sure you're plugged in?)"
            time == 0.toLong() -> "Your battery is already charged beyond this point!"
            else -> {
                val seconds = time/1000
                val minutes = (seconds % 3600) * 60
                val hours = seconds/3600
                "Set a timer for $hours:$minutes"
            }
        }

    }

}