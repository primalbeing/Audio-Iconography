package com.audioiconography.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    private const val CHANNEL_ID = "audio_icon_channel"
    private const val NOTIFICATION_ID = 1

    fun showNotification(context: Context, isConnected: Boolean) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Notification Channel for Android 8+ (Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Audio Icon",
                NotificationManager.IMPORTANCE_LOW // Silent, stays in status bar
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Choose icon based on connection status
        val icon = if (isConnected) R.drawable.ic_headphones else R.drawable.ic_no_headphones

        // Build the notification (silent, stays in status bar)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle("Audio Icon")
            .setContentText(if (isConnected) "🎧 Headphones Connected" else "❌ Headphones Disconnected")
            .setPriority(NotificationCompat.PRIORITY_LOW) // Prevents heads-up popup
            .setOngoing(true) // Makes it persistent
            .build()

        if (isConnected) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        } else {
            notificationManager.cancel(NOTIFICATION_ID)
        }
    }
}
