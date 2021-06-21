package com.pf.aforo.data.model

import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.text.CaseMap
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.pf.aforo.R
import com.pf.aforo.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService as FirebaseMessagingService1

class MyFirebaseMessaging: FirebaseMessagingService1() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG,"onNewToken: " + token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "MENSAJE RECIBIDO!...")
        var notificacion: RemoteMessage.Notification = remoteMessage.getNotification()!!
        var title = notificacion.getTitle()!!
        var msg = notificacion.getBody()!!

        sendNotification(title, msg)
    }

    private fun sendNotification(title: String, msg: String ) {

        var intent: Intent = Intent(this, MainActivity.class)
        var pendingIntent: PendingIntent = PendingIntent.getActivity(this, MyNotification.NOTIFICACION_ID, intent, PendingIntent.FLAG_ONE_SHOT)

        var notificacion: MyNotification = MyNotification(this, MyNotification.CHANNEL_ID_NOTIFICATIONS)
        notificacion.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent)
        notificacion.addChannel("Notificaciones", NotificationManager.IMPORTANCE_DEFAULT)
        notificacion.createChannelGroup(MyNotification.CHANNEL_GROUP_GENERAL, R.String.notification_channel_group_general)
        notificacion.show(MyNotification.CHANNEL_ID)


    }

}