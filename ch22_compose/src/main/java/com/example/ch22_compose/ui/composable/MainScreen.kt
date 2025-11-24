package com.example.ch22_compose.ui.composable

import Item
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ch22_compose.MyApplication
import com.example.ch22_compose.model.ItemModel
import com.example.ch22_compose.model.PageListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.listOf



@SuppressLint("UnrememberedMutableState") //화면 전환 등으로 onCreate가 한 번 더 실행됐을 때 서버에 재접속 하는 것 등을 막으려고  / remember를 쓸 필요가 없음
@Composable
fun MainScreen(modifier: Modifier = Modifier){
    val datas = mutableStateOf(listOf<ItemModel>())

    LaunchedEffect(true) { // 파라미터를 true로 설정하여 앱 시작 시 1번 실행 키가 바뀌지 않으므로 재실행 X
        val call: Call<PageListModel> = MyApplication.networkService.getList(
            MyApplication.QUERY,
            MyApplication.API_KEY,
            1,
            10
        )
        call?.enqueue(object : Callback<PageListModel>{ //비동기로 처리, 응답이 오면 datas의 value를 업데이트되면
            override fun onResponse(
                call: Call<PageListModel>,
                response: Response<PageListModel>) {
                if(response.isSuccessful){
                    datas.value = response.body()?.articles?: listOf()

                }
            }

            override fun onFailure(call: Call<PageListModel?>, t: Throwable) {
                Log.d("kkang","error")
            }
        })
    }

    LazyColumn(modifier = modifier) {                               //리사이클러뷰처럼 데이터를 출력
        itemsIndexed(datas.value){index, item->
            Item(item)
            if(index < datas.value.lastIndex)
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)

        }
    }

}