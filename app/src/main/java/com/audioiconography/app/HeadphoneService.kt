package com.audioiconography.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class HeadphoneService : Service() {
    private val channelId = "headphoneServiceChannel"

    override fun onCreate() {
        super.onCreate()
        Log.d("HeadphoneService", "✅ Service created")
        createNotificationChannel()
        checkHeadphoneState()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("HeadphoneService", "✅ Service started")
        checkHeadphoneState()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // ✅ Fix: Implement onBind() correctly
        return null
    }

    private fun createNotificationChannel() {
        Log.d("HeadphoneService", "✅ Creating notification channel")
        val channel = NotificationChannel(
            channelId,
            "Headphone Detection Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun checkHeadphoneState() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val isWiredHeadsetConnected = isHeadphonesConnected(audioManager)

        Log.d("HeadphoneService", "🎧 Headphone check: Connected = $isWiredHeadsetConnected")

        if (isWiredHeadsetConnected) {
            Log.d("HeadphoneService", "✅ Starting foreground service with notification")
            startForeground(1, buildNotification())
        } else {
            Log.d("HeadphoneService", "❌ No headphones detected, stopping service")
            stopSelf()
        }
    }

    private fun isHeadphonesConnected(audioManager: AudioManager): Boolean {
        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        return devices.any {
            it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES || it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET
        }
    }

    private fun buildNotification(): Notification {
        Log.d("HeadphoneService", "✅ Building notification")
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_headphones)
            .setContentTitle("🎧 Headphones Connected")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }
}
