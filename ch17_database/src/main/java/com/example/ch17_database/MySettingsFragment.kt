package com.example.ch17_database

import android.os.Bundle
import android.preference.SwitchPreference
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.util.prefs.Preferences

class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // "id" 키를 가진 EditTextPreference 찾기
        val idPref: EditTextPreference? = findPreference("id")
        // [수정] "color" 키를 가진 ListPreference 찾기
        val colorPref: ListPreference? = findPreference("color")


        val syncTimePref: EditTextPreference? = findPreference("syncTime")


//        // 이제 각 타입에 맞는 작업을 수행할 수 있습니다.
//        idPref?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
//        colorPref?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        colorPref?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        idPref?.summaryProvider = Preference.SummaryProvider<EditTextPreference>{
            preference ->
            val text = preference.text
            if(TextUtils.isEmpty(text)){
                "설정이 되지 않았습니다."
            }else{
                "설정한 ID 값은: $text 입니다."
            }
        }

        syncTimePref?.summaryProvider = Preference.SummaryProvider<EditTextPreference>{
            preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)){
                "시간이 설정이 되지 않았습니다."
            }else{
                "$text 시간"
            }
        }





    }
}