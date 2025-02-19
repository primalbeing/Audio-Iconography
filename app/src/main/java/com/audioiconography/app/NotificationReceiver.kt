package com.audioiconography.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        when (intent.action) {
            "com.audioiconography.STOP_SERVICE" -> {
                val stopIntent = Intent(context, HeadphoneService::class.java)
                context.stopService(stopIntent)
            }
        }
    }
}
