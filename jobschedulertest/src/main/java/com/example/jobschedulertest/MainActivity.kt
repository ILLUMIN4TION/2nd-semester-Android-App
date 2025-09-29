package com.example.jobschedulertest

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        Log.d("MyJobService","MainActivity-onCreate()...")
        scheduleJob()
    }

    private fun scheduleJob(){
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE)as JobScheduler
        val component = ComponentName(this, MyJobService::class.java)

        val extras = PersistableBundle()
        extras.putString("extra_data", "Hello JobScheduler") // 파라미터로 데이터를 전달

        val info = JobInfo.Builder(1, component)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setMinimumLatency(5_000L) //5초후 실행
            .setExtras(extras)
            .build()

        jobScheduler.schedule(info)
    }
}