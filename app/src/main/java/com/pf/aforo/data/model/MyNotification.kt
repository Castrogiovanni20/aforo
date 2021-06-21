package com.pf.aforo.data.model

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.pf.aforo.R

class MyNotification (context: Context, channelId: String)  {

    public val CHANNEL_ID_NOTIFICATIONS = "channel_id_notifications"
    public val CHANNEL_GROUP_GENERAL = "channel_group_general"
    public val NOTIFICATION_ID = 1

    lateinit var notificationBuilder: NotificationCompat.Builder
    lateinit var notificationManager: NotificationManager
    lateinit var channel: NotificationChannel
    lateinit var context: Context
    lateinit var channelId: String

/*   */
    init {
        this.notificationBuilder = NotificationCompat.Builder(context, channelId)
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)
        this.context = context
        this.channelId = channelId
    }


    public fun createChannelGroup(groupId: String, groupNameId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var groupName: CharSequence = context.getString(groupNameId)
            notificationManager.createNotificationChannelGroup(NotificationChannelGroup(groupId, groupName))
            channel.setGroup(groupId)
        }
    }


    public fun buil(imgId: Int, title:String, content: String, pendingIntent: PendingIntent) {
        notificationBuilder.setSmallIcon(imgId)
            .setColor(context.getResources().getColor(R.color.colorAccent))
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(content))

    }


    public fun addChannel(channelName: CharSequence, importance: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = NotificationChannel(channelId, channelName, importance)
        }
    }


    public fun setVibrate() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel.enableVibration(true)
            // JAVA notificationBuilder.setVibrate(new long[]{1000, 1000});
            // channel.vibrationPattern(LongArray(1000, 1000))
        } /*else {
            notificationBuilder.setVibrate(LongArray(1000, 1000))
        }*/
    }


    public fun setSound(sound: Uri, set: Boolean) {
        if(!set) {
            return
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            var audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build()
            channel.setSound(sound, audioAttributes)
        } else {
            notificationBuilder.setSound(sound)
        }
    }


    public fun show(idAlert: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(idAlert,notificationBuilder.build())
    }


}