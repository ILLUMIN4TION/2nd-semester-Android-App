package com.example.ch15_musicplayer2

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ch15_musicplayer2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var myBindService: AudioService? = null

    // isService의 초기값은 false로 설정하는 것이 더 정확합니다.
    var isService: Boolean = false

    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as AudioService.MyBindService
            myBindService = binder.getService()
            isService = true
            Log.d("BoundService", "연결되었습니다.")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isService = false
            myBindService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // onCreate에서 미리 서비스에 바인딩합니다.
        val serviceIntent = Intent(this, AudioService::class.java)
        bindService(serviceIntent, connection, BIND_AUTO_CREATE)

        binding.aidlPlay.setOnClickListener {
            Log.d("ForegroundService", "Service Start Button Clicked")
            val intent = Intent(this, AudioService::class.java)
            ContextCompat.startForegroundService(this, intent)

            // --- 이 부분에 로그 출력 코드 추가 ---
            // 서비스가 연결되었는지 확인 후, 음원 길이 가져오기
            if (isService && myBindService != null) {
                // 잠시 후 재생이 시작된 다음 길이를 가져오기 위해 약간의 딜레이를 줍니다. (선택사항이지만 안정적)
                binding.root.postDelayed({
                    val maxDuration = myBindService?.getMaxDuration()
                    if (maxDuration != null && maxDuration > 0) {
                        val seconds = maxDuration / 1000
                        Log.d("BoundService", "음원 총 길이: ${seconds}초")
                    } else {
                        Log.d("BoundService", "음원 길이를 가져올 수 없습니다. (재생 시작 전일 수 있습니다)")
                    }
                }, 100) // 0.1초 딜레이
            } else {
                Log.d("BoundService", "서비스가 아직 연결되지 않았습니다.")
            }
            // --- 여기까지 추가 ---
        }

        binding.aidlStop.setOnClickListener {
            Log.d("ForegroundService", "Service Stop Button Clicked")
            val intent = Intent(this, AudioService::class.java)
            stopService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 액티비티가 종료될 때 서비스 바인딩 해제
        if (isService) {
            unbindService(connection)
        }
    }
}
