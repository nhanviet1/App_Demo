package com.example.servicetestapp

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class Notification : Application() {

    var channelID = "ABC"
    var channelID2 = "Lmeow"

    override fun onCreate() {
        super.onCreate()
        notification()
    }

    private fun notification() {
        val channel = NotificationChannel(channelID, "Catto", NotificationManager.IMPORTANCE_HIGH)
        val channel2 = NotificationChannel(channelID2, "Lmeow", NotificationManager.IMPORTANCE_DEFAULT)
//        channel.setSound(null, null)
        channel.description = "ABCDE"
        channel.setShowBadge(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
        manager.createNotificationChannel(channel2)
    }
}
