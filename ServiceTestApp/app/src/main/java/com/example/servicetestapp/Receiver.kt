package com.example.servicetestapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService

class Receiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val actionMusic = p1?.getIntExtra("actionMusic", 0)
        val intentService = Intent(p0, Service::class.java)
        Log.d("Lmeow", "Receiver actionMusic: $actionMusic")
        intentService.putExtra("actionMusic", actionMusic)
        p0?.startService(intentService)
    }
}