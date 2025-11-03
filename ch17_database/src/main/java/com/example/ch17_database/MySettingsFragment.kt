package com.example.ch17_database

import android.os.Bundle
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

        // 이제 각 타입에 맞는 작업을 수행할 수 있습니다.
        idPref?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
        colorPref?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    }
}