package com.example.restfullsimple

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.let

class BatteryListener(context: Context) {
    val battMan = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        context.registerReceiver(null, ifilter)
    }
    val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

    @RequiresApi(Build.VERSION_CODES.P)
    val fullTime: Long = battMan.computeChargeTimeRemaining() // May not autoupdate
    var per: Int? = 0
        set(value) {
            field = when {
                level == -1 || scale == -1 -> null
                else -> level / scale * 100
            }
        }
    @RequiresApi(Build.VERSION_CODES.P)
    fun getTime (percent: Percent) : Int? {

        val timeToTarget = when{
            per == null -> return per
            else -> {
                val notNull : Int = per!!
                (percent.threshold?.minus(notNull)?.div((1-notNull))?.times(fullTime))?.toInt()
            }
        }
        return timeToTarget
    }
}