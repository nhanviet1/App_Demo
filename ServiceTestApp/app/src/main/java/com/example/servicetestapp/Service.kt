package com.example.servicetestapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import java.util.*


class Service : Service() {
    private lateinit var media: MediaPlayer
    private val actionPause = 0
    private val actionResume = 1
    private var isPlaying = false
    private lateinit var songHolder: SongModel
    private var labelHolder = String()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
//        Log.d("Lmeow", "OnCreate is Called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val label = intent?.getStringExtra("Label")
        if (label != null) {
            labelHolder = label
        }

//        val intent = Intent(this, Receiver::class.java)
//        intent.putExtra("label", labelHolder)
//        PendingIntent.getBroadcast()

        val bundle = intent?.extras
        if (bundle != null) {
            val song = bundle.get("Song")
            if (song != null) {
                songHolder = song as SongModel
                startMusic(song)
                sendNotification(song)
            }
        }
        val actionMusic = intent?.getIntExtra("actionMusic", 0)
        Log.d("Lmeow", "actionMusic: $actionMusic")
        actionControl(actionMusic!!)
        return START_NOT_STICKY
    }


    private fun startMusic(song: SongModel) {
        media = MediaPlayer.create(applicationContext, song.resource)
        media.start()
        isPlaying = true
    }

    private fun sendNotification(noti: SongModel) {
        val intent = Intent(this, ForegroundService::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val remoteView = RemoteViews(packageName, R.layout.layout_small)
        val remoteView1 = RemoteViews(packageName, R.layout.layout_medium)
        val remoteView2 = RemoteViews(packageName, R.layout.layout_big)
        val bitmap = BitmapFactory.decodeResource(resources, noti.image)

        //small
        remoteView.setTextViewText(R.id.tvTitle, noti.title)
        remoteView.setImageViewBitmap(R.id.imgView, bitmap)
        if (isPlaying) {
            remoteView.setOnClickPendingIntent(R.id.play, getPendingIntent(this, actionPause))
            remoteView.setImageViewResource(
                R.id.play,
                R.drawable.ic_baseline_pause_circle_outline_24
            )
        } else {
            remoteView.setOnClickPendingIntent(R.id.play, getPendingIntent(this, actionResume))
            remoteView.setImageViewResource(
                R.id.play,
                R.drawable.ic_baseline_play_circle_outline_24
            )
        }

        //medium
        remoteView1.setTextViewText(R.id.tvTitle, noti.title)
        remoteView1.setImageViewBitmap(R.id.imgView, bitmap)
        if (isPlaying) {
            remoteView1.setOnClickPendingIntent(R.id.play, getPendingIntent(this, actionPause))
            remoteView1.setImageViewResource(
                R.id.play,
                R.drawable.ic_baseline_pause_circle_outline_24
            )

        } else {
            remoteView1.setOnClickPendingIntent(R.id.play, getPendingIntent(this, actionResume))
            remoteView1.setImageViewResource(
                R.id.play,
                R.drawable.ic_baseline_play_circle_outline_24
            )
        }

        //big
        remoteView2.setTextViewText(R.id.tvTitle, noti.title)
        remoteView2.setImageViewBitmap(R.id.imgView, bitmap)
        if (isPlaying) {
            remoteView2.setOnClickPendingIntent(R.id.play, getPendingIntent(this, actionPause))
            remoteView2.setImageViewResource(
                R.id.play,
                R.drawable.ic_baseline_pause_circle_outline_24
            )
        } else {
            remoteView2.setOnClickPendingIntent(R.id.play, getPendingIntent(this, actionResume))
            remoteView2.setImageViewResource(
                R.id.play,
                R.drawable.ic_baseline_play_circle_outline_24
            )
        }


        val notificationSmall = NotificationCompat.Builder(this, Notification().channelID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_accessible_forward_24)
            .setCustomContentView(remoteView)
            .setSound(null)
            .build()

        val notificationMedium = NotificationCompat.Builder(this, Notification().channelID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_accessible_forward_24)
            .setCustomBigContentView(remoteView2)
            .setCustomHeadsUpContentView(remoteView1)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(null)
            .build()

        val notificationBig = NotificationCompat.Builder(this, Notification().channelID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_accessible_forward_24)
            .setCustomBigContentView(remoteView2)
            .setSound(null)
            .build()

//        val id = Date().time.toInt()

//        val notificationX = NotificationCompat.Builder(this, Notification().channelID2)
//            .setContentTitle("Lmeow notification")
//            .setContentText("Chonky Cat zzzzzzzzzzxzxzxzxzxzxzxzxzxzxz \n zxzxzxzxz")
//            .setSmallIcon(R.drawable.ic_baseline_accessible_forward_24)
//            .setContentIntent(pendingIntent)
//            .build()
//        getSystemService(NotificationManager::class.java).notify(id, notificationX)

        when (labelHolder.toInt()) {
            0 -> startForeground(1, notificationSmall)
            1 -> startForeground(2, notificationMedium)
            2 -> startForeground(3, notificationBig)
        }


    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent {
        val intent = Intent(context, Receiver::class.java)
        intent.putExtra("actionMusic", action)
        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun actionControl(action: Int) {
        when (action) {
            actionPause -> pauseMusic()
            actionResume -> resumeMusic()
        }
    }

    private fun pauseMusic() {
        if (isPlaying) {
            media.pause()
            isPlaying = false
            sendNotification(songHolder)
        }
    }

    private fun resumeMusic() {
        if (!isPlaying) {
            media.start()
            isPlaying = true
            sendNotification(songHolder)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
        Log.d("Lmeow", "onDestroy is Called")
    }
}