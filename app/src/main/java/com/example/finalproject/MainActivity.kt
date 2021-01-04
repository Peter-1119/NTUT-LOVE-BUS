package com.example.finalproject

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var dbrw: SQLiteDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbrw = FavoriteSQLiteOpenHelper(this).writableDatabase
        //FavoriteSQLiteOpenHelper(this).onUpgrade(dbrw,0,1)
        startService(Intent(this@MainActivity, MyService::class.java).putExtra("flag", true))

        btn_map.setOnClickListener{
            var intent = Intent(this, BusStopFind::class.java)
            startActivity(intent)
        }

        btn_weather.setOnClickListener {
            var intent = Intent(this, Show_Weather2::class.java)
            startActivity(intent)
        }

        //設置動態介面
        val ImageView = findViewById<GifImageView>(R.id.imageView7)
        try {
            val gifDrawable = GifDrawable(resources, R.drawable.test2)
            ImageView.setImageDrawable(gifDrawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //顯示天氣圖示
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, WeatherFragment2())
                .commit()
        }


        //切換天氣頁面
        btn_weather.setOnClickListener(View.OnClickListener {
            startActivityForResult(
                Intent(
                    this,
                    Show_Weather2::class.java
                ), 1
            )
        })

        btn_flavor.setOnClickListener {
            var intent = Intent(this,Favorite::class.java)
            startActivity(intent)
        }
    }
}