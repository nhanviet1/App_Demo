package com.example.servicetestapp.boundservice

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.servicetestapp.Notification
import com.example.servicetestapp.R
import com.example.servicetestapp.databinding.ActivityBoundServiceBinding

class BoundService : AppCompatActivity() {
    private lateinit var binding: ActivityBoundServiceBinding
    private var isServiceConnected = false
    private lateinit var mService: ServiceOfBound

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as ServiceOfBound.MyBinder
            mService = binder.getBoundService()
//            mService.startMusic()
            mService.sendNoti()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isServiceConnected = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoundServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.buttonStart.setOnClickListener {
            val intent = Intent(this, ServiceOfBound::class.java)
            bindService(intent, connection, BIND_AUTO_CREATE)
        }

        binding.buttonStop.setOnClickListener {
            if (isServiceConnected) {
                unbindService(connection)
                isServiceConnected = false
                Log.d("Lmeow", "boundService is stopped")
            }
        }
    }
}