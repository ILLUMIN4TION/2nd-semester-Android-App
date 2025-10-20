package com.example.ch15_musicplayer

import android.app.Service
import android.content.Intent
import android.os.IBinder
//
//Todo 서비스 클래스 구현
class MyAudioService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        return super.onStartCommand(intent, flags, startId)
    }


}