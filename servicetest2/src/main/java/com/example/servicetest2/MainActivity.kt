package com.example.servicetest2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var myService: MyService? = null
    var isService = false

    fun callServiceFunction(view: View){
        if (isService){
            val message = myService?.serviceMessage()
            Log.d("BoundService", "message=${message}")
        }else{
            Log.d("BoundService", "서비스가 연결되지 않았습니다.")
        }
    }



    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyService.MyBinder
            myService = binder.getService()
            isService = true
            Log.d("BoundService", "연결되었습니다.")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isService = false
        }

    }

    fun serviceBind(view: View){
        val intent = Intent(this, MyService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun serviceUnbind(view: View){
        if(isService){
            unbindService(connection)
            isService = false
        }
    }

    fun serviceStart(view: View){
        val intent = Intent(this, MyService::class.java)
        intent.action = MyService.ACTION_START
        startService(intent)
    }
    fun serviceStop(view:View){
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

    }


}