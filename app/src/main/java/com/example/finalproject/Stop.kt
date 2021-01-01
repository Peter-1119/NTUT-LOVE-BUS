package com.example.finalproject

import android.util.Log

class Stop(var stop: String) {
    //One stop two direction
    private val searchBus = SearchBus()
    var numberBuses = 0
    var busList: MutableList<Bus> = mutableListOf()

    init {
        createBusList()
    }

    private fun createBusList() {
        searchBus.AddStopName(stop)
        val stopData = searchBus.getStopsData()
        searchBus.RemoveStopName(stop)
        numberBuses = stopData?.size!!
        val busesNameSet = mutableSetOf<String>()
        for (i in stopData.indices) {
            busesNameSet.add(stopData[i].RouteName!!.Zh_tw!!)
        }
        for (name in busesNameSet) {
            searchBus.AddBus(name)
        }
        println("searchBus_BusList${searchBus.bus_list}")
        val busData = searchBus.getDirectionsData()
        for (name in busesNameSet) {
            val indexBusData = indexFindName(busData, name)
            val departureStopName = busData!![indexBusData!!].DepartureStopNameZh!!
            val destinationStopName = busData!![indexBusData!!].DestinationStopNameZh!!

            val indexStartData = indexFindNameDir(stopData, name, 0)
            val indexEndData = indexFindNameDir(stopData, name, 1)
            Log.d("Test","${name}:${indexStartData}+${indexEndData}")
            var newBus: Bus? = null
            if (indexStartData != null && indexEndData != null)
                newBus = Bus(stopData[indexStartData!!], stopData[indexEndData!!], "往${departureStopName}", "往${destinationStopName}")
            else if (indexStartData == null && indexEndData != null)
                newBus = Bus(null, stopData[indexEndData!!], "往${departureStopName}", "往${destinationStopName}")
            else if (indexStartData != null && indexEndData == null)
                newBus = Bus(stopData[indexStartData!!], null, "往${departureStopName}", "往${destinationStopName}")
            else
                Log.d("Test", "newBus Error")

            busList.add(newBus!!)
        }
    }

    fun updateBusList() {
        searchBus.AddStopName(stop)
        val stopData = searchBus.getStopsData()
        searchBus.RemoveStopName(stop)
        numberBuses = stopData?.size!!

        for (i in busList) {
            val indexStartData = indexFindNameDir(stopData, i.name, 0)
            val indexEndData = indexFindNameDir(stopData, i.name, 1)
            if (indexStartData != null && indexEndData != null)
                i.update(stopData[indexStartData], stopData[indexEndData])
            else if (indexStartData == null && indexEndData != null)
                i.update(null, stopData[indexEndData])
            else if (indexStartData != null && indexEndData == null)
                i.update(stopData[indexStartData], null)
            else
                Log.d("Test", "updateBus Error")
        }
    }

    private fun indexFindName(data: Array<BusRoute>?, name: String): Int? {
        for (i in data!!.indices) {
            if (data[i].RouteName!!.Zh_tw == name) {
                return i
            }
        }
        return null
    }

    private fun indexFindNameDir(data: Array<StopID>?, name: String, Dir: Int): Int? {
        for (i in data!!.indices) {
            if (data[i].RouteName!!.Zh_tw == name && data[i].Direction == Dir) {
                return i
            }
        }
        return null
    }

    fun getStatusName(busIndex: Int, dir: Int): String {
        var status = "";
        var time: Int = 0
        if (dir == 0) {
            if (busList[busIndex].toStartStatus == 0) {
                time = getTime(busIndex, 0)
                if (time == 0)
                    status = "即將進站"
                else
                    status = "${time}分鐘後到"
            } else
                status = busList[busIndex].startStatusName
        } else if (dir == 1) {
            if (busList[busIndex].toEndStatus == 0) {
                time = getTime(busIndex, 1)
                if (time == 0)
                    status = "即將進站"
                else
                    status = "${time}分鐘後到"
            } else
                status = busList[busIndex].endStatusName
        }
        return status
    }

    fun getTime(busIndex: Int, dir: Int): Int {
        var time: Int = 0
        if (dir == 0) {
            time = busList[busIndex].toStartTime / 60
        } else if (dir == 1)
            time = busList[busIndex].toEndTime / 60
        Log.d("Test", "Time${dir}:${time}")
        return time
    }

    fun getStatus(busIndex: Int, dir: Int): Int {
        var status: Int = 0
        if (dir == 0) {
            status = busList[busIndex].toStartStatus!!
        } else if (dir == 1)
            status = busList[busIndex].toEndStatus!!
        return status
    }
}

class Bus(var toStartDirData: StopID?, var toEndDirData: StopID?, val startName: String, val endName: String = "") {
    //StopStatus (integer, optional): 車輛狀態備註 : [0:'正常',1:'尚未發車',2:'交管不停靠',3:'末班車已過',4:'今日未營運'] ,
    var name: String = ""
    var toStartTime = 0
    var toEndTime = 0
    var startStatusName = ""
    var endStatusName = ""
    var toStartStatus = 4
    var toEndStatus = 4

    init {
        Log.d("Test","start:${toStartDirData}")
        Log.d("Test","end:${toEndDirData}")
        if (toStartDirData != null) {
            name = toStartDirData!!.RouteName!!.Zh_tw!!
            toStartStatus = toStartDirData!!.StopStatus
        } else if (toEndDirData != null) {
            name = toEndDirData!!.RouteName!!.Zh_tw!!
            toEndStatus = toEndDirData!!.StopStatus
        } else
            Log.d("Test", "BusNameError")

        updateStatusName()
    }

    fun update(newStart: StopID?, newEnd: StopID?) {
        toStartDirData = newStart
        toEndDirData = newEnd
        if (toStartDirData != null) {
            toStartStatus = toStartDirData!!.StopStatus
        } else if (toEndDirData != null) {
            toEndStatus = toEndDirData!!.StopStatus
        } else
            Log.d("Test", "statusError")
        updateStatusName()
    }

    private fun updateStatusName() {
        when (toStartStatus) {
            0 -> {
                toStartTime = toStartDirData!!.EstimateTime //Sec
                startStatusName = "正常"
            }
            1 -> startStatusName = "尚未發車"
            2 -> startStatusName = "交管不停靠"
            3 -> startStatusName = "末班車已過"
            else -> startStatusName = "今日不營運"
        }
        when (toEndStatus) {
            0 -> {
                toEndTime = toEndDirData!!.EstimateTime //Sec
                endStatusName = "正常"
            }
            1 -> endStatusName = "尚未發車"
            2 -> endStatusName = "交管不停靠"
            3 -> endStatusName = "末班車已過"
            else -> endStatusName = "今日不營運"
        }
    }
}
