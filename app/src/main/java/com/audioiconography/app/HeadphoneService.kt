package com.audioiconography.app

import android.app.*
import android.content.*
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class HeadphoneService : Service() {

    private lateinit var audioManager: AudioManager
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Register BroadcastReceiver for headphone state changes
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == AudioManager.ACTION_HEADSET_PLUG) {
                    val state = intent.getIntExtra("state", -1)
                    updateNotification(state == 1)
                }
            }
        }

        val filter = IntentFilter(AudioManager.ACTION_HEADSET_PLUG)
        registerReceiver(receiver, filter)

        startForegroundService()
    }

    private fun startForegroundService() {
        val channelId = "audio_icon_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Audio Icon Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Audio Icon Running")
            .setSmallIcon(if (isHeadphoneConnected()) R.drawable.ic_headphones else R.drawable.ic_no_headphones)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }

    private fun updateNotification(isHeadphoneConnected: Boolean) {
        val notification = NotificationCompat.Builder(this, "audio_icon_channel")
            .setContentTitle("Audio Icon Running")
            .setSmallIcon(if (isHeadphoneConnected) R.drawable.ic_headphones else R.drawable.ic_no_headphones)
            .setOngoing(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }

    private fun isHeadphoneConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
                .any { it.type == android.media.AudioDeviceInfo.TYPE_WIRED_HEADSET || it.type == android.media.AudioDeviceInfo.TYPE_WIRED_HEADPHONES }
        } else {
            @Suppress("DEPRECATION")
            audioManager.isWiredHeadsetOn
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
