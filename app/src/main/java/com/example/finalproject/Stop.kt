package com.example.finalproject

import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.finalproject.Stop.Companion.api


class Stop(var pos: String) {
    companion object{
        lateinit var api:API
    }
    val busList: MutableList<Bus> = mutableListOf()

    init {
        api=API(pos)
        getBusList()
    }

    private fun getBusList() {
        api.getStopList(pos)
        for (i in 0 until api.busNumber) {
            val stopList = api.stopList
            val newBus = Bus(stopList, api.busesName[i])
            busList.add(newBus)
        }
    }

    fun getTime(busIndex: Int, dir: Int): Int {
        var time = 0;
        //TODO:busUpdate 更改成busUpdate_to、busUpdate_t1
        if (dir == 0) {
            busList[busIndex].busUpdate()
            time = busList[busIndex].toStartTime
        } else if (dir == 1)
            time = busList[busIndex].toEndTime
        return time
    }
}

class Bus(val stopList: List<String>, val name: String) {
    var toStartTime = 0
    var toEndTime = 0

    //TODO:busUpdate 更改成busUpdate_to、busUpdate_t1
    fun busUpdate() {
        //Log.d("Test",name)
        api.updateTime(name)
        toStartTime = api.t0;
        toEndTime = api.t1;
        Log.d("Test", String.format("%d、%d", toStartTime, toEndTime))
    }
}

class API(var pos: String) {
    companion object{
        val requestHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg);
                var updateLists = msg.obj as String
                Log.d("API","HandlerTest")
                println(updateLists)
            }
        }
    }

    var searchBus = SearchBus()

    //TODO:假想API


    var t0 = 0
    var t1 = 0

    //假想BussName，從站牌得到的公車列表
    var busNumber = 5 //數量
    val busesName = arrayListOf("222", "72", "123", "262", "212") //名稱
    //TODO:以stop(地點)決定公車數量和名稱

    //取得該公車的去程時間和回程時間
    //TODO:API的時間表示確認
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateTime(bus: String) {

        Log.d("API", "pos:" + pos)
        searchBus.AddStopName(pos)
        searchBus.getStopsData()
        Log.d("API", "數量" + searchBus.StopData?.size.toString())

        if (bus == "222") {
            t0 = (1..10).random()
            t1 = (1..10).random()
        } else if (bus == "72") {
            t0 = (1..10).random()
            t1 = (1..10).random()
        }
    }

    val stopList: MutableList<String> = mutableListOf()
    fun getStopList(bus: String) {
        stopList.add("台北科技大學站(忠孝)")
        stopList.add("台北科技大學站(建國)")
        stopList.add("忠孝新生捷運站")
        stopList.add("光華商場")
        stopList.add("懷生國宅")
    }
}
