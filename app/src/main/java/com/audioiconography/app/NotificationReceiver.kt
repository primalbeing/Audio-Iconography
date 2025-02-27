package com.audioiconography.app

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getIntExtra("state", -1)

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (state == 0) {
            nm.cancel(1) // Remove notification when unplugged
            return
        }

        val channelId = "headphone_channel"
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
        )

        val notification = Notification.Builder(context)
            .setSmallIcon(R.drawable.ic_headphones)
            .setContentTitle(context.getString(R.string.channel_name))
            .setContentText(context.getString(R.string.channel_description))
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Makes notification persistent
            .setAutoCancel(false) // Prevents accidental removal

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(channelId)
        }

        nm.notify(1, notification.build())
    }
}
