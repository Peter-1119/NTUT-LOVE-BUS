package com.example.finalproject

import android.util.Log

public class Stop(pos: String) {
    private val stopPos: String = pos
    private val api = API()
    val busList: MutableList<Bus> = mutableListOf()


    init {
        getBusList()
    }

    private fun getBusList() {
        api.getStopList(stopPos)
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
    private val api = API()

    //TODO:busUpdate 更改成busUpdate_to、busUpdate_t1
    fun busUpdate() {
        //Log.d("Test",name)
        api.updateTime(name)
        toStartTime = api.t0;
        toEndTime = api.t1;
        Log.d("Test", String.format("%d、%d", toStartTime, toEndTime))
    }
}

class API() {
    //TODO:假想API
    var stop = ""
    val stopList: MutableList<String> = mutableListOf()

    var t0 = 0
    var t1 = 0

    //假想BussName，從站牌得到的公車列表
    var busNumber = 5 //數量
    val busesName = arrayListOf("222", "72", "123", "456", "789") //名稱
    //TODO:以stop(地點)決定公車數量和名稱


    fun getStopList(bus: String) {
        stopList.add("大我新舍")
        stopList.add("麟光站")
        stopList.add("黎忠市場")
        stopList.add("富陽街口")
        stopList.add("捷運六張犁站(和平)")
        stopList.add("和平安和路口")
        stopList.add("臥龍街")
    }

    //取得該公車的去程時間和回程時間
    //TODO:API的時間表示確認
    fun updateTime(bus: String) {
        if (bus == "222") {
            t0 = (1..10).random()
            t1 = (1..10).random()
        } else if (bus == "72") {
            t0 = (1..10).random()
            t1 = (1..10).random()
        }
    }
}