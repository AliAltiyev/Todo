package com.altyyev.todo.todo.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.altyyev.todo.R
import com.altyyev.todo.todo.ui.activity.MainActivity
import com.altyyev.todo.todo.util.Constants.Companion.NOTIFICATION_ID

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, i: Intent?) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context!!, NOTIFICATION_ID)
            .setContentTitle("Todo Alarm")
            .setSmallIcon(R.drawable.time_icon)
            .setContentText("You have set up alarm")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, notification.build())
        setNotificationSound(context)
    }

    private fun setNotificationSound(context: Context) {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = RingtoneManager.getRingtone(context, soundUri)
        notification?.play()
    }

}