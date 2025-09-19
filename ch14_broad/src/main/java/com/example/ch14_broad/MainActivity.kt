package com.example.ch14_broad

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ch14_broad.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // OnOffReceiver가 있다면 그대로 유지합니다. (화면 켜짐/꺼짐 감지)
    private lateinit var onoffReceiver: OnOffReceiver
    private lateinit var binding: ActivityMainBinding
    private var batteryLevelReceiver: BatteryLevelReceiver? = null

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate 호출됨")

        // OnOffReceiver 등록 (필요한 경우)
        onoffReceiver = OnOffReceiver() // OnOffReceiver 클래스가 프로젝트에 정의되어 있어야 합니다.
        val screenFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(onoffReceiver, screenFilter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(onoffReceiver, screenFilter)
        }
        Log.d(TAG, "OnOffReceiver 등록됨")

        // MyReceiver 호출 및 알림 권한 요청 로직은
        // 배터리 정보 UI 업데이트와 직접적인 관련이 없으므로,
        // 필요 없다면 이 부분(버튼 리스너 및 permissionLauncher)은 제거해도 됩니다.
        // 만약 버튼 클릭 시 알림을 보내는 기능이 여전히 필요하다면 유지합니다.
        /*
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value }) {
                val intent = Intent(this, MyReceiver::class.java) // MyReceiver.kt 파일이 있어야 함
                sendBroadcast(intent)
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.POST_NOTIFICATIONS"
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(this, MyReceiver::class.java)
                    sendBroadcast(intent)
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            "android.permission.POST_NOTIFICATIONS"
                        )
                    )
                }
            } else {
                val intent = Intent(this, MyReceiver::class.java)
                sendBroadcast(intent)
            }
        }
        */
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume 호출됨")
        // BatteryLevelReceiver 등록
        if (batteryLevelReceiver == null) {
            batteryLevelReceiver = BatteryLevelReceiver(
                binding.chargingResultView, // activity_main.xml에 정의된 ID와 일치해야 함
                binding.chargingImageView,  // activity_main.xml에 정의된 ID와 일치해야 함
                binding.percentResultView   // activity_main.xml에 정의된 ID와 일치해야 함
            )
        }
        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryLevelReceiver, batteryFilter)
        Log.d(TAG, "BatteryLevelReceiver 등록됨")
        // ACTION_BATTERY_CHANGED는 sticky broadcast이므로, 등록 시점에 마지막 상태를 바로 받아서
        // onReceive가 호출되어 UI가 업데이트됩니다. 따라서 여기서 별도로 UI를 초기화할 필요가 없습니다.
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause 호출됨")
        // BatteryLevelReceiver 해제
        batteryLevelReceiver?.let {
            unregisterReceiver(it)
            Log.d(TAG, "BatteryLevelReceiver 해제됨")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy 호출됨")
        // OnOffReceiver 해제 (등록했다면)
        unregisterReceiver(onoffReceiver)
        Log.d(TAG, "OnOffReceiver 해제됨")
    }
}