
package com.pf.aforo.data.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.pf.aforo.R
import com.pf.aforo.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService as FirebaseMessagingService1


class MyFirebaseMessaging: FirebaseMessagingService1() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: " + token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "MENSAJE RECIBIDO!...")
        var notificacion: RemoteMessage.Notification = remoteMessage.getNotification()!!
        var title = notificacion.getTitle()!!
        var msg = notificacion.getBody()!!

        sendNotification(title, msg)
    }

    private fun sendNotification(title: String, msg: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            MyNotification.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notification = MyNotification(this, MyNotification.CHANNEL_ID_NOTIFICATIONS)
        notification.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent)
        notification.addChannel("Notificaciones", NotificationManager.IMPORTANCE_DEFAULT)
        notification.createChannelGroup(
            MyNotification.CHANNEL_GROUP_GENERAL,
            R.string.notification_channel_group_general
        )
        notification.show(MyNotification.NOTIFICATION_ID)
    }
}