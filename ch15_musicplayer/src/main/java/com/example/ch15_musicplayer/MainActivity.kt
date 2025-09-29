package com.example.ch15_musicplayer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch15_musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.foregroundPlay.setOnClickListener {
            //Todo 서비스 실행로직 구현
        }

        binding.foregroundStop.setOnClickListener {
            //Todo 서비스 종료 로직 구현
        }

    }

}