package com.example.finalproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test_main.*


class TestMain : AppCompatActivity(){
    companion object {
        lateinit var dbrw: SQLiteDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main)

        dbrw = FavoriteSQLiteOpenHelper(this).writableDatabase


        var stopName = arrayOf("臺北科技大學(忠孝)","台北科技大學站(建國)","忠孝新生捷運站","光華商場","松江新生幹線","懷生國宅")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, stopName)
        spinner2.adapter = adapter
        button.setOnClickListener {
            var bundle = Bundle()
            Log.d("Test","select:${stopName[spinner2.selectedItemId.toInt()]}")
            bundle.putString("Stop",stopName[spinner2.selectedItemId.toInt()])
            var intent = Intent(this,BusInformation::class.java)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        button2.setOnClickListener {
            var intent = Intent(this,Favorite::class.java)
            startActivity(intent)
        }

    }
}