package com.audioiconography.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class HeadphoneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_HEADSET_PLUG) {
            val state = intent.getIntExtra("state", -1)
            Log.d("HeadphoneReceiver", "Headset state changed: $state")

            when (state) {
                1 -> {
                    Log.d("HeadphoneReceiver", "🎧 Headphones Connected")
                    NotificationHelper.showNotification(context, true)
                }
                0 -> {
                    Log.d("HeadphoneReceiver", "❌ Headphones Disconnected")
                    NotificationHelper.showNotification(context, false)
                }
                else -> Log.d("HeadphoneReceiver", "Unknown headset state")
            }
        }
    }
}
