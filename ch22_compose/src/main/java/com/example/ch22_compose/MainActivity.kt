package com.example.ch22_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.ch22_compose.ui.composable.MainScreen
import com.example.ch22_compose.ui.theme.AndroidLabTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { //setContet안의 내용을로 activity 화면을 채우겠다
            AndroidLabTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            // 1. 상단바에 표시될 제목
                            title = {
                                // 3. 이 Text 부분을 수정합니다.
                                Text(
                                    text = "뉴스 앱",
                                    color = Color.White,          // 글자색을 흰색으로 지정
                                    fontWeight = FontWeight.Bold  // 글자 두께를 볼드체로 지정
                                )
                            },
                            // 2. 상단바의 배경색과 제목 색상을 app 테마에 맞춤
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                ) { innerPadding ->
                    MainScreen(
                        //name = "Android",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidLabTheme {
        Greeting("Android")

    }
}