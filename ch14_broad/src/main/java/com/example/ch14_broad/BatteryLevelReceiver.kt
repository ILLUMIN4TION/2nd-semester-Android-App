package com.example.ch14_broad // 본인 프로젝트의 패키지명으로 변경하세요

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.ImageView
import android.widget.TextView
import android.util.Log

class BatteryLevelReceiver(
    // Activity의 UI 요소들을 전달받기 위한 파라미터
    private val chargingResultView: TextView,
    private val chargingImageView: ImageView,
    private val percentResultView: TextView
) : BroadcastReceiver() {

    companion object {
        const val TAG = "BatteryLevelReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            Log.d(TAG, "배터리 상태 변경 감지됨: ${intent.action}")

            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

            if (isCharging) {
                val chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                when (chargePlug) {
                    BatteryManager.BATTERY_PLUGGED_USB -> {
                        chargingResultView.text = "USB Plugged"
                        // 프로젝트의 drawable 폴더에 'usb_icon.xml' 또는 'usb_image.png' 같은
                        // USB 아이콘 이미지가 있다면 아래 주석을 해제하고 파일명을 맞추세요.
                        // chargingImageView.setImageResource(R.drawable.usb_icon)
                    }
                    BatteryManager.BATTERY_PLUGGED_AC -> {
                        chargingResultView.text = "AC Plugged"
                        // 프로젝트의 drawable 폴더에 'ac_adapter_icon.xml' 또는 'ac_image.png' 같은
                        // AC 어댑터 아이콘 이미지가 있다면 아래 주석을 해제하고 파일명을 맞추세요.
                        // chargingImageView.setImageResource(R.drawable.ac_adapter_icon)
                    }
                    else -> {
                        chargingResultView.text = "Charging"
                        chargingImageView.setImageDrawable(null) // 또는 기본 충전 아이콘
                    }
                }
            } else {
                chargingResultView.text = "Not Plugged"
                chargingImageView.setImageDrawable(null) // 또는 기본 '플러그 없음' 아이콘
            }

            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

            if (level != -1 && scale != -1) {
                val batteryPct = level / scale.toFloat() * 100
                percentResultView.text = "${batteryPct.toInt()} %"
            } else {
                percentResultView.text = "N/A" // 정보를 가져올 수 없는 경우
            }
        }
    }
}
