package com.example.ch14_broad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class OnOffReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent?.action){
            Intent.ACTION_SCREEN_ON -> {
                Log.d("OnOffReceiver", "화면이 켜졌습니다.")

            }
            Intent.ACTION_SCREEN_OFF -> {
                Log.d("OnOffReceiver", "화면이 꺼졌습니다.")

            }
        }
    }
}