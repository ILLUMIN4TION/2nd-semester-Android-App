package com.example.ch15_musicplayer2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class Foreground : Service() {

    val CHANNEL_ID = "ForegroundChannel"
    lateinit var player: MediaPlayer


    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        createNotificationChannel()

    }

    fun createNotificationChannel(){
        Log.e("ForegroundService","createNotificationChannel() called")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(
                NotificationManager::class.java
            )
            Log.e("ForegroundService","createNotificationChannel() called")
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("ForegroundService","onStartCommand() called")

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setSmallIcon(com.example.ch15_musicplayer2.R.mipmap.ic_launcher_round)
            .build()

        startForeground(1, notification)


        if(!player.isPlaying){
            player = MediaPlayer.create(this@Foreground, com.example.ch15_musicplayer2.R.raw.music)
            try{
                player.start()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }


        return   super.onStartCommand(intent, flags, startId)

    }
    override fun onDestroy() {
        if(player.isPlaying)
            player.stop()

        player.release()
        Log.e("Foreground", "onDestroy called")
        super.onDestroy()
    }
}