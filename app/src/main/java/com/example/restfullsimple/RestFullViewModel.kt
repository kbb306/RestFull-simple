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
        val time = batt.getTime(model)
        val hour =  when {
            time == null -> "Nan"
            else -> time.div(60).toString()
        }
        val minute = when {
            time == null -> "NaN"
            else -> (time.rem(60)).times(60).toString()
        }
        return when{
            batt.isCharging -> "Set a timer for $hour:$minute"
            else -> "Cannot use when phone is not charging"
        }

    }

}