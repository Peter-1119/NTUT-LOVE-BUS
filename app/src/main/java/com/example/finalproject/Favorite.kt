package com.example.finalproject

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.favorite_page.*

class Favorite : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_page)
        val actionbar = supportActionBar
        actionbar!!.title = "我的最愛"
        actionbar.setDisplayHomeAsUpEnabled(true)
        //宣告Adapter並連結ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

    }
    //Create Refresh Button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item.itemId)
        return when (item.itemId) {
            R.id.refresh -> {
                Log.d("Tab","Refresh")
                val c = BusInformation.dbrw.rawQuery("SELECT * FROM myTable",null)
                //從第一筆開始輸出
                c.moveToFirst()
                //清空舊資料
                items.clear()
                showToast("共有${c.count}筆資料")
                for (i in 0 until c.count) {
                    items.add("公車:${c.getString(0)}")
                    //移動到下一筆
                    c.moveToNext()
                }
                //更新列表資料
                adapter.notifyDataSetChanged()
                //關閉Cursor
                c.close()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //Click Back Button
    //TODO:Back Button Function
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun showToast(text: String) =
            Toast.makeText(this,text, Toast.LENGTH_LONG).show()
}