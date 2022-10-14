package com.example.servicetestapp.backgroundservice

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.servicetestapp.databinding.ActivityBackgroundServiceBinding

class BackgroundServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBackgroundServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBackgroundServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.buttonStart.setOnClickListener {
            val intent = Intent(this, BackgroundService::class.java)
            startService(intent)
        }

        val minutes = 5 * 60 * 1000
        binding.buttonStartBySchedule.setOnClickListener {
            val componentName = ComponentName(this, JobService::class.java)
            val jobInfo = JobInfo.Builder(1, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED).setPersisted(true)
                .build()

            val jobScheduler: JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }

        binding.buttonStopBySchedule.setOnClickListener {
            val jobScheduler: JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(1)
        }
    }
}