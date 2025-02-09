package com.audioiconography.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class HeadphoneService : Service() {
    private val CHANNEL_ID = "headphone_service"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        checkHeadphoneState()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        checkHeadphoneState()
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Headphone Detection Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun checkHeadphoneState() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS)
        val isWiredHeadsetConnected = devices.any {
            it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET || it.type == AudioDeviceInfo.TYPE_USB_HEADSET
        }

        if (isWiredHeadsetConnected) {
            startForeground(1, buildNotification())
        } else {
            stopSelf() // Stop service when headphones are disconnected
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_headphones)
            .setContentText("🎧 Headphones Connected")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Keep it persistent
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
