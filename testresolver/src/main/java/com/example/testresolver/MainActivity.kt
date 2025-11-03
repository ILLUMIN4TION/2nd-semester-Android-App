package com.example.testresolver

import android.R
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testresolver.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var storagePermission: ActivityResultLauncher<String>
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        storagePermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission())
        {isGranted ->
            if(isGranted){
                startProcess()
            }else{
                Toast.makeText(baseContext, "외부 저장소 승인해야 앱 사용 가능", Toast.LENGTH_LONG).show()
                finish()
                //토스트 출력 및 앱 종료
            }
        }

        storagePermission.launch(android.Manifest.permission.READ_MEDIA_AUDIO)


    }
    fun startProcess(){
        Log.d(localClassName, "cr ===> startProcess")
        val adapter = MusicRecyclerAdapter()
        adapter.musicList.addAll(getMusicList()) //Music 타입 list에 getMusicList 메서드로 모든 노래 리스트를 담아옴
        binding.recyclerView.adapter = adapter // 어댑터 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun getMusicList(): List<Music>{
        val listUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION

        )

        val cursor = contentResolver.query(listUrl, proj, null, null, null)
        val musicList = mutableListOf<Music>()

        while (cursor?.moveToNext() == true){
            val id = cursor.getString(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val albumId = cursor.getString(3)
            val duration = cursor.getLong(4)

            val music = Music(id, title, artist, albumId, duration)
            musicList.add(music)
        }
        return musicList

    }
}