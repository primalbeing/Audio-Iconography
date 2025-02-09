package com.audioiconography.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class HeadphoneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DEBUG", "HeadphoneReceiver triggered!")

        if (intent.action == Intent.ACTION_HEADSET_PLUG) {
            val state = intent.getIntExtra("state", -1)
            Log.d("DEBUG", "Headset state changed: $state")

            when (state) {
                1 -> {
                    Log.d("DEBUG", "🎧 Headphones Connected - Showing Notification")
                    NotificationHelper.showNotification(context, true)
                }
                0 -> {
                    Log.d("DEBUG", "❌ Headphones Disconnected - Removing Notification")
                    NotificationHelper.showNotification(context, false)
                }
                else -> Log.d("DEBUG", "Unknown headset state")
            }
        } else {
            Log.d("DEBUG", "Intent action did not match HEADSET_PLUG")
        }
    }
}
