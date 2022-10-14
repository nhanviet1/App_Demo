package com.example.servicetestapp

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.servicetestapp.backgroundservice.BackgroundServiceActivity
import com.example.servicetestapp.boundservice.BoundService
import com.example.servicetestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val catCall = "android.intent.action.CATCALL"
    val receiver by lazy {
        Receiver2()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        val a = IntentFilter()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).run {
            addAction(catCall)
            registerReceiver(receiver, this)
        }

//        Intent(catCall).run {
//            putExtra("Cat", "My Cat is mlem")
//            sendBroadcast(this)
//        }
    }

    private fun setupView() {
        binding.foreground.setOnClickListener {
            startActivity(Intent(this, ForegroundService::class.java))
        }
        binding.bound.setOnClickListener {
            startActivity(Intent(this, BoundService::class.java))
        }
        binding.background.setOnClickListener {
            startActivity(Intent(this, BackgroundServiceActivity::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

}