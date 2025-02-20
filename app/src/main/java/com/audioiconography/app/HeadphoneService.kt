package com.audioiconography.app

import android.app.*
import android.content.*
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class HeadphoneService : Service() {

    private lateinit var audioManager: AudioManager
    private lateinit var receiver: BroadcastReceiver
    private val CHANNEL_ID = "audio_icon_channel"

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // FIX #1: START FOREGROUND SERVICE IMMEDIATELY
        startForegroundServiceSafe()

        // Register BroadcastReceiver for headphone state changes
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == AudioManager.ACTION_HEADSET_PLUG) {
                    val state = intent.getIntExtra("state", -1)
                    if (state == 1) {
                        showNotification()
                    } else if (state == 0) {
                        stopForeground(true) // Remove notification when unplugged
                    }
                }
            }
        }

        val filter = IntentFilter(AudioManager.ACTION_HEADSET_PLUG)
        registerReceiver(receiver, filter)

        // Fix #2: Manually check if headphones are already connected
        if (isHeadphoneConnected()) {
            showNotification()
        }
    }

    // Ensures Foreground Service is started safely
    private fun startForegroundServiceSafe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Audio Icon Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setSound(null, null)  // Silent notification
                enableVibration(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Audio Iconography Running")
            .setSmallIcon(R.drawable.ic_headphones)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(1, notification)
    }

    // FIX #2: REQUEST NOTIFICATION PERMISSION
    fun requestNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent = Intent("android.settings.APP_NOTIFICATION_SETTINGS").apply {
                putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
            }
            context.startActivity(intent)
        }
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Headphones Connected")
            .setSmallIcon(R.drawable.ic_headphones)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(1, notification)
    }

    // Fix #3: Reliable Headphone Detection for All Devices
    private fun isHeadphoneConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
                .any { it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET || it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES }
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // Ensures service restarts if killed
    }
}
