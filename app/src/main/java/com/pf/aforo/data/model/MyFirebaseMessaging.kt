
package com.pf.aforo.data.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.RemoteMessage
import com.pf.aforo.R
import com.pf.aforo.ui.MainActivity
import com.pf.aforo.ui.home.supervisor.AddSucursalViewModel
import com.google.firebase.messaging.FirebaseMessagingService as FirebaseMessagingService1


class MyFirebaseMessaging: FirebaseMessagingService1() {



    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: " + token)

    }


    fun getToken(): String {

        //FirebaseApp.initializeApp(this)

        lateinit var token: String

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivity", "error al obtener el token", task.exception)
                return@OnCompleteListener
            }
            token = task.result!!.token
            Log.d("MainActivity", token)

        })
        return token
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