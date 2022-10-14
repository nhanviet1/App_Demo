package com.example.servicetestapp.boundservice

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.servicetestapp.ForegroundService
import com.example.servicetestapp.Notification
import com.example.servicetestapp.R
import com.example.servicetestapp.SongModel

class ServiceOfBound : Service() {
    private lateinit var media: MediaPlayer
    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getBoundService(): ServiceOfBound = this@ServiceOfBound
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Lmeow BoundService", "OnCreate is Called")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    fun startMusic() {
            media = MediaPlayer.create(applicationContext, R.raw.stand_by_me)
            media.start()
        Log.d("Lmeow BoundService", "HEHE")
    }

    fun sendNoti(){
        val notificationX = NotificationCompat.Builder(this@ServiceOfBound, Notification().channelID)
            .setContentTitle("Lmeow notification")
            .setContentText("Bound Service just have started")
            .setSmallIcon(R.drawable.ic_baseline_accessible_forward_24)
            .build()
        getSystemService(NotificationManager::class.java).notify(88, notificationX)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lmeow BoundService", "onDestroy is Called")
//        media.release()
    }
}