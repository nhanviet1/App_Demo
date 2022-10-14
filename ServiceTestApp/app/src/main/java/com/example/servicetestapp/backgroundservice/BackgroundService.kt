package com.example.servicetestapp.backgroundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.example.servicetestapp.R

class BackgroundService : Service() {

    private lateinit var media: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startMusic()
        return START_NOT_STICKY
    }

    private fun startMusic() {
        Log.d("Lmeow", "Background Music Started")
        media = MediaPlayer.create(applicationContext, R.raw.stand_by_me)
        media.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
        Log.d("Lmeow", "onDestroy Called")
    }
}