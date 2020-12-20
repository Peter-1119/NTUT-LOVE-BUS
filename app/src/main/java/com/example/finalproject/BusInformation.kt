package com.example.finalproject

import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.fragments.TimeFragment
import com.example.finalproject.fragments.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_bus_information.*

class BusInformation : AppCompatActivity() {
    var pos=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_information)
        val actionbar = supportActionBar
        actionbar!!.title = "公車動態介面"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //從地圖點站牌切換過來
        //TODO:之後用Intent + Bundle來取得站牌資訊stop
        pos="行天宮"
        val stop = Stop(pos)
        //TODO:將List的資料給Spinner
        val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.lunch,
                android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TimeFragment(), "往起點")
        adapter.addFragment(TimeFragment(), "往終點")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    //Create Refresh Button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    //Click Back Button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

class Stop(pos: String) {
    val stopPos: String = pos
    val busList: MutableList<Bus> = mutableListOf()
    val api = API()

    init {
        GetBusList()
    }

    fun GetBusList() {
        api.getStopList(stopPos)
        for (i in 0 until api.bustNumber) {
            val stopList = api.stopList
            val newBus = Bus(stopList)
            busList.add(newBus)
        }
    }

    fun GetTime(busIndex: Int, dir: Int): Int {
        var time = 0;
        busList[busIndex].BusUpdate()
        if (dir == 0)
            time = busList[busIndex].toStartTime
        else if (dir == 1)
            time = busList[busIndex].toEndTime
        return time
    }
}

class Bus(val stopList: List<String>) {
    val name = ""
    var toStartTime = 0
    var toEndTime = 0
    val api = API()

    fun BusUpdate() {
        api.UpdateTime(name)
        toStartTime = api.t0;
        toEndTime = api.t1;
    }
}

class API() {
    //TODO:假想API
    var Stop = ""
    val stopList: MutableList<String> = mutableListOf()
    var bustNumber = 0
    var t0 = 0
    var t1 = 0

    fun getStopList(pos: String) {
        Stop = pos
        bustNumber = 5
        stopList.add("大我新舍")
        stopList.add("麟光站")
        stopList.add("黎忠市場")
        stopList.add("富陽街口")
        stopList.add("捷運六張犁站(和平)")
        stopList.add("和平安和路口")
        stopList.add("臥龍街")
    }

    fun UpdateTime(bus: String) {
        t0 = 432
        t1 = 421
    }
}