package com.audioiconography.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class HeadphoneService : Service() {

    private val channelId = "headphoneServiceChannel"
    private val notificationId = 1  // Declare a notificationId

    override fun onCreate() {
        super.onCreate()
        Log.d("HeadphoneService", "Service created")

        createNotificationChannel()

        // 1. Immediately start in the foreground with a temporary "Checking..." notification
        startForeground(notificationId, buildNotification("Checking headphones..."))

        // 2. Now check headphone state and update or stop
        checkHeadphoneState()
    }

    override fun onStartCommand(intent: android.content.Intent?, flags: Int, startId: Int): Int {
        Log.d("HeadphoneService", "Service started")
        // Optionally re-check headphones if the service is restarted
        checkHeadphoneState()
        return START_NOT_STICKY
    }

    override fun onBind(intent: android.content.Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
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
        val isConnected = isHeadphonesConnected(audioManager)
        Log.d("HeadphoneService", "Headphone check: Connected = $isConnected")

        if (isConnected) {
            // Update the existing notification text
            val manager = getSystemService(NotificationManager::class.java)
            manager.notify(notificationId, buildNotification("🎧 Headphones Connected"))
        } else {
            // Stop the service if no headphones are connected
            stopForeground(true)
            stopSelf()
        }
    }

    private fun isHeadphonesConnected(audioManager: AudioManager): Boolean {
        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        return devices.any {
            it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                    it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                    it.type == AudioDeviceInfo.TYPE_USB_HEADSET
        }
    }

    /**
     * buildNotification takes a content string to show dynamic text.
     */
    private fun buildNotification(content: String): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_headphones)
            .setContentTitle(content)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }
}
