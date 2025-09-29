package com.example.jobschedulertest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyJobService", "onCreate()...")

    }

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d("MyJobService", "onStartJob()")
        Log.d("MyjobService", "extra_data: ${params.extras.getString("extra_data")}") // 파라미터에 담은 데이터를 출력
        Thread(Runnable {
            var sum = 0
            for(i in 1..10) {
                Log.d("MyjobService", "i: $i")
                sum += i
                Thread.sleep(1000)
            }
            Log.d("MyJobService", "sum: $sum")
            jobFinished(params, false)
            }).start()

        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.d("MyJobService", "onStopJob()")
        return false
    }



//    override fun onBind(intent: Intent): IBinder {
//        TODO("Return the communication channel to the service.")
//    }

    override fun onDestroy() {
        super.onDestroy()
    }
}