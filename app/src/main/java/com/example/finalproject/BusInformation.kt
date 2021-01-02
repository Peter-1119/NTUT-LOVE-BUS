package com.example.finalproject

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
import kotlinx.android.synthetic.main.activity_bus_information.*

class BusInformation : AppCompatActivity() {

    var pos = ""
    private var viewPageAdapter = ViewPagerAdapter(supportFragmentManager)
    private lateinit var stop: Stop
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_information)

        val actionbar = supportActionBar
        actionbar!!.title = "公車動態介面"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //從地圖點站牌切換過來
        pos = intent.getBundleExtra("bundle")!!.getString("Stop")!!
        stop = Stop(pos)
        //Spinner
        val busesName: MutableList<String> = mutableListOf()
        for (i in stop.busList.indices) {
            busesName.add(stop.busList[i].name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, busesName)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val bus: String = parent?.getItemAtPosition(position).toString()
                //setToast(bus)
                //創建畫面時會自己更新一次
                Log.d("Tab", "SpinnerSelected")
                updateTab(stop, viewPageAdapter)
            }
        }
    }
    private fun updateTab(stop: Stop, viewPageAdapter: ViewPagerAdapter) {
        viewPageAdapter.removeFragment()
        stop.busList[spinner.selectedItemId.toInt()].toStartDirData?.let {
            var tabStart = TimeFragment()
            val bundleStart = Bundle()
            bundleStart.putInt("TIME", stop.getTime(spinner.selectedItemId.toInt(), 0))
            bundleStart.putString("StatusName", stop.getStatusName(spinner.selectedItemId.toInt(), 0))
            bundleStart.putString("BusName", stop.busList[spinner.selectedItemId.toInt()].name)
            bundleStart.putString("DirName", stop.busList[spinner.selectedItemId.toInt()].startName)
            bundleStart.putInt("Status", stop.getStatus(spinner.selectedItemId.toInt(), 0))
            bundleStart.putString("Stop",pos)
            bundleStart.putInt("Dir",0)
            tabStart.arguments = bundleStart
            viewPageAdapter.addFragment(tabStart, stop.busList[spinner.selectedItemId.toInt()].startName)
        }
        stop.busList[spinner.selectedItemId.toInt()].toEndDirData?.let {
            var tabEnd = TimeFragment()
            val bundleEnd = Bundle()
            bundleEnd.putInt("TIME", stop.getTime(spinner.selectedItemId.toInt(), 1))
            bundleEnd.putString("StatusName", stop.getStatusName(spinner.selectedItemId.toInt(), 1))
            bundleEnd.putString("BusName", stop.busList[spinner.selectedItemId.toInt()].name)
            bundleEnd.putString("DirName", stop.busList[spinner.selectedItemId.toInt()].endName)
            bundleEnd.putInt("Status", stop.getStatus(spinner.selectedItemId.toInt(), 1))
            bundleEnd.putString("Stop",pos)
            bundleEnd.putInt("Dir",1)
            tabEnd.arguments = bundleEnd
            viewPageAdapter.addFragment(tabEnd, stop.busList[spinner.selectedItemId.toInt()].endName)
        }

        viewPager.adapter = viewPageAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun setToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
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
                Log.d("Tab", "Refresh")
                setToast("請稍後，更新資料中")
                stop.updateBusList()
                updateTab(stop, viewPageAdapter)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //Click Back Button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

