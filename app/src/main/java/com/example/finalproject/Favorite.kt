package com.example.finalproject

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.favorite_page.*

class Favorite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_page)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
                arrayListOf("訊息1","訊息2","訊息3","訊息4","訊息5","訊息6"))
    }
}