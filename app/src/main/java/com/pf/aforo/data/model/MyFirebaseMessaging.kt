
package com.pf.aforo.data.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.pf.aforo.R
import com.pf.aforo.ui.login.LoginFragment
import com.pf.aforo.ui.home.supervisor.AddSucursalViewModel
import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseMessaging: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: " + token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "Mensaje recibido")
        val data = remoteMessage.data
        if (data.size > 0) {
            Log.d(TAG, "data: $data")
            val title = data["title"]
            val msg = data["message"]
            sendNotification(title!!, msg!!)
        } else {
            val notification = remoteMessage.notification
            val title = notification!!.title
            val msg = notification!!.body
            sendNotification(title!!, msg!!)
        }
    }

    private fun sendNotification(title: String, msg: String) {
        val intent = Intent(this, LoginFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            MyNotification.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notification = MyNotification(this, MyNotification.CHANNEL_ID_NOTIFICATIONS)
        notification.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent)
        notification.addChannel("Notificaciones", NotificationManager.IMPORTANCE_HIGH)
        notification.createChannelGroup(
            MyNotification.CHANNEL_GROUP_GENERAL,
            R.string.notification_channel_group_general
        )
        notification.show(MyNotification.NOTIFICATION_ID)
    }




}