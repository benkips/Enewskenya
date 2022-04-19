package com.mabnets.e_newskenya.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mabnets.e_newskenya.R
import com.mabnets.e_newskenya.Utils.getBitmapfromUrl
import com.mabnets.e_newskenya.index
import kotlin.random.Random

private const val CHANNEL_ID="mychannel"
class FirebaseService : FirebaseMessagingService() {
    val TAG = "FCMService"
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(TAG,"From: ${message.from}")
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data["image"]}")
        }

        val intent= Intent(this,index::class.java).putExtra("url",message.data.get("link").toString())

        val notificationmanager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID= Random.nextInt()
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val myicon = BitmapFactory.decodeResource(resources, R.drawable.logo)
        val bitmaps= getBitmapfromUrl(message.data.get("image"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationchannnel(notificationmanager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification= NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(message.data.get("title"))
            .setContentText(message.data.get("message"))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmaps)
                    .bigLargeIcon(myicon)

            )
            .setLargeIcon(bitmaps)
            .build()

        notificationmanager.notify(notificationID, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private  fun createNotificationchannnel(notificationManager: NotificationManager){
        val channelName="ChannelName"
        val channel=
            NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description="Enews kenya"
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)
    }
}