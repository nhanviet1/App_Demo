package com.example.servicetestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.servicetestapp.databinding.ActivityForegroundServiceBinding

class ForegroundService : AppCompatActivity() {
    private lateinit var binding: ActivityForegroundServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForegroundServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.buttonSmall.setOnClickListener {
            startService(0)
        }

        binding.buttonMedium.setOnClickListener {
            startService(1)
        }

        binding.buttonBig.setOnClickListener {
            startService(2)
        }

        binding.buttonStop.setOnClickListener {
            stopService()
        }
    }

    private fun startService(id: Int) {
        Log.d("Lmeow", "OnCreate is Called")
        val intent = Intent(this, Service::class.java)
        val song = SongModel("Stand by me", R.drawable.cat, R.raw.stand_by_me)
        intent.putExtra("Label", "$id")
        val bundle = Bundle()
        bundle.putSerializable("Song", song)
        intent.putExtras(bundle)
        startService(intent)
    }

    private fun stopService() {
        val intent = Intent(this, Service::class.java)
        stopService(intent)
    }

}