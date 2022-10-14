package com.example.servicetestapp.backgroundservice

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class JobService: JobService() {

    private var jobCancel = false

    override fun onStartJob(p0: JobParameters?): Boolean {
        backgroundWork(p0)
        return true
    }

    private fun backgroundWork(p0: JobParameters?) {
        Thread {
            for (i in (1..40)) {
                if (!jobCancel) {
                    Log.d("Lmeow", "$i")
                    Thread.sleep(1000)
                }
            }
            jobFinished(p0, false)
        }.start()
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        jobCancel = true
        return true
    }
}