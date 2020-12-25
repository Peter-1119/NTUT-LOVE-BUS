package com.example.finalproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.fragments.TimeFragment
import com.example.finalproject.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_bus_information.*

class BusInformation : AppCompatActivity() {
    companion object {
        public lateinit var dbrw: SQLiteDatabase
    }
    var pos = ""
    private var viewPageAdapter = ViewPagerAdapter(supportFragmentManager)
    private lateinit var stop: Stop
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_information)
        val actionbar = supportActionBar
        actionbar!!.title = "公車動態介面"
        actionbar.setDisplayHomeAsUpEnabled(true)
        //取得資料庫實體
        dbrw = FavoriteSQLiteOpenHelper(this).writableDatabase
        //TODO:測試用
        FavoriteSQLiteOpenHelper(this).onUpgrade(dbrw,0,1)
        //從地圖點站牌切換過來
        //TODO:之後用Intent + Bundle來取得站牌資訊stop的pos

        pos = "行天宮"
        stop = Stop(pos)
        //Spinner
        val busesName: MutableList<String> = mutableListOf()
        for (i in stop.busList.indices) {
            busesName.add(stop.busList[i].name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, busesName)
        spinner.adapter = adapter

        //Tab的顯示頁面設置
        var tabStart = TimeFragment()
        val bundleStart = Bundle()
        bundleStart.putInt("TIME", stop.getTime(spinner.selectedItemId.toInt(), 0))
        bundleStart.putString("BusName",stop.busList[spinner.selectedItemId.toInt()].name)
        bundleStart.putString("Dir","往起點")
        tabStart.arguments = bundleStart
        //TODO:往[API拿地點]
        viewPageAdapter.addFragment(tabStart, "往起點")

        //終點
        var tabEnd = TimeFragment()
        val bundleEnd = Bundle()
        bundleEnd.putInt("TIME", stop.getTime(spinner.selectedItemId.toInt(), 1))
        bundleEnd.putString("BusName",stop.busList[spinner.selectedItemId.toInt()].name)
        bundleEnd.putString("Dir","往終點")
        tabEnd.arguments = bundleEnd
        viewPageAdapter.addFragment(tabEnd, "往終點")

        viewPager.adapter = viewPageAdapter
        tabs.setupWithViewPager(viewPager)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val bus: String = parent?.getItemAtPosition(position).toString()
                setToast(bus)
                if (bus=="123"){
                    var bundle = Bundle()
                    bundle.putString("key","This is String")
                    bundle.putInt("key1",1)
                    var intent = Intent(this@BusInformation,Favorite::class.java)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                }
                Log.d("Tab","SpinnerSelected")
                updateTab(stop,viewPageAdapter)
            }
        }
        tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("Tab","Change Tab")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("Tab","onTabUnselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("Tab","onTabReselected")
            }
        })

    }
    //TODO:只針對目前所選擇TAB更新
    private fun updateTab(stop:Stop,viewPageAdapter:ViewPagerAdapter) {
        var tabStart = TimeFragment()
        val bundleStart = Bundle()
        bundleStart.putInt("TIME", stop.getTime(spinner.selectedItemId.toInt(), 0))
        bundleStart.putString("BusName",stop.busList[spinner.selectedItemId.toInt()].name)
        bundleStart.putString("Dir","往起點")
        tabStart.arguments = bundleStart
        //TODO:往[API拿地點]
        viewPageAdapter.changeFragment(0,tabStart, "往起點")

        //終點
        var tabEnd = TimeFragment()
        val bundleEnd = Bundle()
        bundleEnd.putInt("TIME", stop.getTime(spinner.selectedItemId.toInt(), 1))
        bundleEnd.putString("BusName",stop.busList[spinner.selectedItemId.toInt()].name)
        bundleEnd.putString("Dir","往終點")
        tabEnd.arguments = bundleEnd
        viewPageAdapter.changeFragment(1,tabEnd, "往終點")

        viewPager.adapter = viewPageAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun setToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
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
                updateTab(stop,viewPageAdapter)
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
}

