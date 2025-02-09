package com.audioiconography.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class HeadphoneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_HEADSET_PLUG) {
            val state = intent.getIntExtra("state", -1)
            when (state) {
                1 -> NotificationHelper.showNotification(context, true) // Headphones connected
                0 -> NotificationHelper.showNotification(context, false) // Headphones disconnected
            }
        }
    }
}