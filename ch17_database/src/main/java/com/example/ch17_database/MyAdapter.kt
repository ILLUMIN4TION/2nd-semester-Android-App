package com.example.ch17_database

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ch17_database.databinding.ItemRecyclerviewBinding


class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>?, val dataNum: MutableList<String>?, context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val db = DBHelper(context).readableDatabase

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position]
        binding.itemNum.text= dataNum!![position]


        binding.itemDelete.setOnClickListener {
            db.execSQL("delete from TODO_TB where todo = ?", arrayOf(datas[position]))
            datas.removeAt(position)
            dataNum.removeAt(position)
            notifyDataSetChanged()
        }
    }
}
