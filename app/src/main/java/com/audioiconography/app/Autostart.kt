package com.audioiconography.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Autostart : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startService(Intent(context, BackgroundService::class.java))
    }
}
