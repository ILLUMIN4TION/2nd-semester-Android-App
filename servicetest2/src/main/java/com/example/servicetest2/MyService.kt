package com.example.servicetest2

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    val binder = MyBinder()

    fun serviceMessage(): String{
        return "Hello Acitivity! i am service"
    }


    inner class MyBinder: Binder(){
        fun getService(): MyService{
            return this@MyService
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("StartService", "action=$action")
        return super.onStartCommand(intent, flags, startId)
    }

    companion object{
        val ACTION_START = "com.example.servicetest2.START"
        val ACTION_RUN = "com.example.servicetest2.RUN"
        val ACTION_STOP = "com.example.servicetest2.STOP"

    }

    override fun onDestroy() {
        Log.d("Service","서비스 종료")
        super.onDestroy()
    }


}