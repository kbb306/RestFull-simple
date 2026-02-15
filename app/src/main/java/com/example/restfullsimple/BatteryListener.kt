package com.example.restfullsimple

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.div
import kotlin.let
import kotlin.times

class BatteryListener(private val context: Context) {
    private fun batteryStatus(): Intent? = context.registerReceiver(null,IntentFilter(Intent.ACTION_BATTERY_CHANGED))


fun isCharging() : Boolean {
    val status = batteryStatus()?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
}

    fun percent() : Int? {
        val level: Int = batteryStatus()?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus()?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        return when {
            level == -1 || scale == -1 -> null
            else -> (level * 100) / scale
        }
    }

    fun fullOrNull() : Long? {
        val battMan = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val fullTime: Long = battMan.computeChargeTimeRemaining()
        return when {
            fullTime > 0 -> fullTime
            else -> null
        }
    }

    fun getTime (percent: Int) : Long? {
        if (!isCharging()) return null
        val current = percent() ?: return null
        if (percent <= current) return 0L

        val time = fullOrNull() ?: return null
        val denom = 100 - current
        if (denom <= 0) return null

        return time*(percent-current).toLong() / denom.toLong()
    }
}