package com.example.ch16_provider

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.ch16_provider.databinding.ActivityMainBinding
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //gallery request launcher..................

        //갤러리 앱에서 돌아왔을 때 무엇을 선택했나?
        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            //미디어 저장소에서 가져온 값에 대한 트라이문
            try{
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap  =  BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                }?:let{
                    Log.d("kkang", "bitmap null")
                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }


        binding.galleryButton.setOnClickListener {
            //gallery app........................

            //ACTION_PICK이라는 액션을 액션에 지정 (무언가를 선택하겠다)
            val intent = Intent(Intent.ACTION_PICK,

                //어떤 리소스에서 PICK 할 것인가?  저장소중 이미지-미디어 카테고리를 가져올게
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"

            //다른 화면이 아닌 다른 앱에서 결과물을 받겠다 .start가 아닌 launch
            requestLauncher.launch(intent)
            
        }






        //camera request launcher.................

        val requestCameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.imgSize),
                resources.getDimensionPixelSize(R.dimen.imgSize)
            )
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let {
                binding.userImageView.setImageBitmap(bitmap)
            }

        }
        
        binding.cameraButton.setOnClickListener {
            //camera app......................
            //파일 준비...............
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
            filePath = file.absolutePath
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.ch16_provider.fileprovider", file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraLauncher.launch(intent)
            
        }
    }

    //우리가 직접 만든 메서드,
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //실제 이미지를 가져오지x 이미지의 정보만 가지고 옴
        try {
            var inputStream = contentResolver.openInputStream(fileUri) //프로바이더 제공하는 것 contentResolver가 받음 ** <-

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}