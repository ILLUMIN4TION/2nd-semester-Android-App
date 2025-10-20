package com.example.ch15_musicplayer2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch15_musicplayer2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.aidlPlay.setOnClickListener {
            Log.e("ForegroundService","Service Start Button Clicked")
            val intent = Intent(this, com.example.ch15_musicplayer2.Foreground::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
        binding.aidlStop.setOnClickListener {
            Log.e("ForegroundService","Service Stop Button Clicked")
            val intent = Intent(this, com.example.ch15_musicplayer2.Foreground::class.java)
            stopService(intent)
        }

    }
}