package com.audioiconography.app

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.IBinder

class BackgroundService : Service() {
    private lateinit var notificationReceiver: NotificationReceiver

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)

        notificationReceiver = NotificationReceiver()
        registerReceiver(notificationReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(notificationReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}
