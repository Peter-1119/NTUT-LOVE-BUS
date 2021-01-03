package com.example.finalproject

import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException
import java.lang.Thread.sleep
import java.security.SignatureException
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SearchBus {

    var num: Int = 0

    //搜尋站牌清單
    val search_list: MutableList<String> = mutableListOf()

    //回傳特定巴士清單
    val bus_list: MutableList<String> = mutableListOf()

    //查詢設定
    val APPID: String = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"
    val APPkey: String = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"
    var x_date: String = getServerTime()
    var SignDate: String = "x-date: " + x_date
    var Signature: String = ""

    //查詢的資料
    var BusData: Array<BusRoute>? = null
    var StopData: Array<StopID>? = null

    //新增要查詢的站
    fun AddStopName(StopName: String) {
        num++
        search_list.add(StopName)
    }

    //新增要查詢的巴士
    fun AddBus(BusID: String) {
        bus_list.add(BusID)
    }

    //移除要查詢的站
    fun RemoveStopName(StopName: String) {
        num--
        search_list.remove(StopName)
    }

    //移除要查詢的巴士
    fun RemoveBus(BusID: String) {
        bus_list.remove(BusID)
    }

    //查詢function
    fun SearchAPI(url: String, choice: Int) {
        //加密簽章
        x_date = getServerTime()
        SignDate = "x-date: " + x_date
        try {
            Signature = Signature_Compute(SignDate, APPkey)
        } catch (e1: SignatureException) {
            e1.printStackTrace();
        }

        val authorization: String = "hmac username=\"" + APPID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\""
        var data: String? = ""
        //println(SignDate)
        //println(authorization)
        //println(x_date)
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .header("Authorization", authorization)
                .header("x-date", x_date)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                if (response!!.code() == 200) {
                    if (response.body() == null) return
                    if (choice == 0) StopData = Gson().fromJson(response.body().string(), Array<StopID>::class.java)
                    if (choice == 1) BusData = Gson().fromJson(response.body().string(), Array<BusRoute>::class.java)
                } else {
                    Log.d("API", "ERROR!response.code()=${response.code()}")
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                println("查詢失敗")
            }
        })
    }

    //查詢站點的公車
    fun getStopsData(): Array<StopID>? {
        var StopNameList: String = ""
        var url: String = ""
        for ((i, content) in search_list.withIndex()) {
            if (i == 0)
                StopNameList += "contains(StopName/Zh_tw,'${content}')"
            else
                StopNameList += " or contains(StopName/Zh_tw,'${content}')"
        }
        url = "https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?\$top=50&\$format=JSON&\$filter=${StopNameList}&\$orderby=RouteName/Zh_tw"
        Log.d("API", "getStopsData url:${url}")
        SearchAPI(url, 0)
        //TODO:時間調整測試
        sleep(1000)
//        for (i in StopData!!.indices) {
//            println("${StopData!![i].StopName?.Zh_tw}站;車號：${StopData!![i].RouteName!!.Zh_tw};需再等候${StopData!![i].EstimateTime}")
//        }
        return StopData
    }

    fun getStopDataByStr(filter: String): Array<StopID>? {
        var url = "https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?\$top=50&\$format=JSON&\$filter=${filter}&\$orderby=RouteName/Zh_tw"
        Log.d("API", "getStopDataByStr url:${url}")
        SearchAPI(url, 0)
        sleep(1000)
        return StopData
    }

    //查詢公車方向
    fun getDirectionsData(): Array<BusRoute>? {
        var BusIDList: String = ""
        for ((i, content) in bus_list.withIndex()) {
            if (i == 0)
                BusIDList += "RouteName/Zh_tw eq '${content}'"
            else
                BusIDList += " or RouteName/Zh_tw eq '${content}'"
//            if(i == 0)
//                BusIDList += "contains(RouteName/Zh_tw,'${content}')"
//            else
//                BusIDList += " or contains(RouteName/Zh_tw,'${content}')"
        }
        var url: String = "https://ptx.transportdata.tw/MOTC/v2/Bus/Route/City/Taipei?&\$top=30&\$format=JSON&\$select=DepartureStopNameZh,DestinationStopNameZh,RouteName&\$filter=${BusIDList}&\$orderby=RouteName/Zh_tw"
        Log.d("API", "getDirectionsData url:${url}")
        SearchAPI(url, 1)

        sleep(1000)
//        for (i in BusData!!.indices) {
//            println("起站是${BusData!![i].DepartureStopNameZh}站;終點站是：${BusData!![i].DestinationStopNameZh};")
//        }

        return BusData
    }

    //查詢時間
    fun getServerTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(calendar.time)
    }

    //加密金要
    @Throws(SignatureException::class)
    fun Signature_Compute(xData: String, AppKey: String): String {
        return try {
            val encoder = Base64.getEncoder()
            // get an hmac_sha1 key from the raw key bytes
            val signingKey = SecretKeySpec(AppKey.toByteArray(charset("UTF-8")), "HmacSHA1")

            // get an hmac_sha1 Mac instance and initialize with the signing key
            val mac = Mac.getInstance("HmacSHA1")
            mac.init(signingKey)

            // compute the hmac on input data bytes
            val rawHmac = mac.doFinal(xData.toByteArray(charset("UTF-8")))
            encoder.encodeToString(rawHmac)
        } catch (e: Exception) {
            throw SignatureException("Failed to generate HMAC : " + e.message)
        }
    }
}

//站牌detail的資料
class StopID {
    var StopUID: String? = null
    var StopID: String? = null
    var StopName: Name? = null
    var RouteUID: String? = null
    var RouteID: String? = null
    var RouteName: Name? = null
    var Direction = 0
    var StopStatus = 0
    var SrcUpdateTime: String? = null
    var UpdateTime: String? = null
    var EstimateTime = 0

    class Name {
        var Zh_tw: String? = null
        var En: String? = null
    }
}

//巴士的detail資料
class BusRoute {
    var RouteUID: String? = null
    var RouteID: String? = null
    var HasSubRoutes: Boolean? = null
    var Operators: operaterID? = null
    var AuthorityID: String? = null
    var ProviderID: String? = null
    var SubRoutes: subroute? = null
    var BusRouteType: Int? = null
    var RouteName: Name? = null
    var DepartureStopNameZh: String? = null
    var DepartureStopNameEn: String? = null
    var DestinationStopNameZh: String? = null
    var DestinationStopNameEn: String? = null
    var TicketPriceDescriptionZh: String? = null
    var TicketPriceDescriptionEn: String? = null
    var FareBufferZoneDescriptionZh: String? = null
    var FareBufferZoneDescriptionEn: String? = null
    var RouteMapImageUrl: String? = null
    var City: String? = null
    var CityCode: String? = null
    var UpdateTime: String? = null
    var VersionID: Int? = null

    class Name {
        var Zh_tw: String? = null
        var En: String? = null
    }

    class subroute {
        var SubRouteUID: String? = null
        var SubRouteID: String? = null
        var OperatorIDs: operaterID? = null
        var SubRouteName: Name? = null
        var Direction: Int? = null
        var FirstBusTime: String? = null
        var LastBusTime: String? = null
        var HolidayFirstBusTime: String? = null
        var HolidayLastBusTime: String? = null

    }

    class operaterID {
        var OperatorIDs: String? = null
    }
}
