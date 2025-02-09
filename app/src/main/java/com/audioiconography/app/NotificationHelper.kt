package com.audioiconography.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

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
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        } else {
            notificationBuilder.setAutoCancel(true) // Allow user to swipe it away
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

            // NEW: Cancel notification to make sure it disappears
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.cancel(NOTIFICATION_ID)
        }
    }
}
