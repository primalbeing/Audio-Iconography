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

        // Have Notification Channel exists
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Audio Icon",
                NotificationManager.IMPORTANCE_LOW // Silent notification
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Select icon and message
        val icon = if (isConnected) R.drawable.ic_headphones else R.drawable.ic_no_headphones
        val text = if (isConnected) "🎧 Headphones Connected" else "❌ Headphones Disconnected"

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        if (isConnected) {
            notificationBuilder.setOngoing(true) // Persistent when connected
        } else {
            notificationBuilder.setAutoCancel(true) // Removable when disconnected
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

        // Have the notification be cleared when headphones are disconnected
        if (!isConnected) {
            notificationManager.cancel(NOTIFICATION_ID)
        }
    }
}
