package com.example.finalproject

import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ProgressBar
import com.google.gson.Gson
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_search_bus.*
import org.json.JSONObject
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.collections.ArrayList
import java.net.URL
import java.security.SignatureException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.xml.sax.Parser as Parser

class search_bus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bus)
        btn_back.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_id.setOnClickListener{
            //information
//            val url : String = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=55ec6d6e-dc5c-4268-a725-d04cc262172b"
            val url : String = "https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?\$filter=StopName/Zh_tw%20eq%20%27%E8%87%BA%E5%8C%97%E7%A7%91%E6%8A%80%E5%A4%A7%E5%AD%B8(%E5%BF%A0%E5%AD%9D)%27&\$top=30&\$format=JSON"
            val APPID : String = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"
            val APPkey : String = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"
            val x_date : String = getServerTime()
            val SignDate : String = "x-date: " + x_date
            //sinature
            var Signature : String = ""
            //
            try{
                Signature = Signature_Compute(SignDate, APPkey)
            }catch (e1: SignatureException){
                e1.printStackTrace();
            }

            val authorization : String = "hmac username=\"" + APPID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\""
            println(Signature)
            println(authorization)
            //Test analysis and output--------------------------------------------
            val requestcontent =
                    "[{\"StopUID\":\"TPE45496\",\"StopID\":\"45496\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"NTUT (Zhongxiao)\"},\"RouteUID\":\"TPE15571\",\"RouteID\":\"15571\",\"RouteName\":{\"Zh_tw\":\"600\",\"En\":\"600\"},\"Direction\":1,\"EstimateTime\":4234,\"StopStatus\":1,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE40585\",\"StopID\":\"40585\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10961\",\"RouteID\":\"10961\",\"RouteName\":{\"Zh_tw\":\"262\",\"En\":\"262\"},\"Direction\":1,\"EstimateTime\":394,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE46014\",\"StopID\":\"46014\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"Taipei university of Technology (MRT Zhongxiao Xinsheng )\"},\"RouteUID\":\"TPE15514\",\"RouteID\":\"15514\",\"RouteName\":{\"Zh_tw\":\"605\",\"En\":\"605\"},\"Direction\":0,\"EstimateTime\":1155,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE125361\",\"StopID\":\"125361\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE16132\",\"RouteID\":\"16132\",\"RouteName\":{\"Zh_tw\":\"212夜\",\"En\":\"212Night\"},\"Direction\":1,\"StopStatus\":1,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE40548\",\"StopID\":\"40548\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10961\",\"RouteID\":\"10961\",\"RouteName\":{\"Zh_tw\":\"262\",\"En\":\"262\"},\"Direction\":0,\"EstimateTime\":847,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE46026\",\"StopID\":\"46026\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"Taipei university of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE15514\",\"RouteID\":\"15514\",\"RouteName\":{\"Zh_tw\":\"605\",\"En\":\"605\"},\"Direction\":1,\"EstimateTime\":1434,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE10676\",\"StopID\":\"10676\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology\"},\"RouteUID\":\"TPE10912\",\"RouteID\":\"10912\",\"RouteName\":{\"Zh_tw\":\"212\",\"En\":\"212\"},\"Direction\":1,\"EstimateTime\":652,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE125332\",\"StopID\":\"125332\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE16132\",\"RouteID\":\"16132\",\"RouteName\":{\"Zh_tw\":\"212夜\",\"En\":\"212Night\"},\"Direction\":0,\"StopStatus\":1,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE19232\",\"StopID\":\"19232\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE15185\",\"RouteID\":\"15185\",\"RouteName\":{\"Zh_tw\":\"202區\",\"En\":\"202Shuttle\"},\"Direction\":1,\"StopStatus\":3,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE46113\",\"StopID\":\"46113\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"Taipei university of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE15518\",\"RouteID\":\"15518\",\"RouteName\":{\"Zh_tw\":\"605新台五\",\"En\":\"605Xintaiwu\"},\"Direction\":0,\"StopStatus\":3,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE10786\",\"StopID\":\"10786\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10911\",\"RouteID\":\"10911\",\"RouteName\":{\"Zh_tw\":\"212直\",\"En\":\"212Express\"},\"Direction\":0,\"EstimateTime\":409,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE19148\",\"StopID\":\"19148\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE15111\",\"RouteID\":\"15111\",\"RouteName\":{\"Zh_tw\":\"202\",\"En\":\"202\"},\"Direction\":1,\"EstimateTime\":183,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE10840\",\"StopID\":\"10840\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10911\",\"RouteID\":\"10911\",\"RouteName\":{\"Zh_tw\":\"212直\",\"En\":\"212Express\"},\"Direction\":1,\"EstimateTime\":1067,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE10234\",\"StopID\":\"10234\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE11411\",\"RouteID\":\"11411\",\"RouteName\":{\"Zh_tw\":\"299\",\"En\":\"299\"},\"Direction\":1,\"EstimateTime\":109,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE36059\",\"StopID\":\"36059\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10417\",\"RouteID\":\"10417\",\"RouteName\":{\"Zh_tw\":\"忠孝幹線\",\"En\":\"Zhongxiao Metro Bus\"},\"Direction\":0,\"EstimateTime\":724,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE40712\",\"StopID\":\"40712\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10962\",\"RouteID\":\"10962\",\"RouteName\":{\"Zh_tw\":\"262區\",\"En\":\"262Shuttle\"},\"Direction\":1,\"EstimateTime\":303,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE45556\",\"StopID\":\"45556\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"NTUT (Zhongxiao)\"},\"RouteUID\":\"TPE15571\",\"RouteID\":\"15571\",\"RouteName\":{\"Zh_tw\":\"600\",\"En\":\"600\"},\"Direction\":0,\"EstimateTime\":2992,\"StopStatus\":1,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE10650\",\"StopID\":\"10650\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology\"},\"RouteUID\":\"TPE10912\",\"RouteID\":\"10912\",\"RouteName\":{\"Zh_tw\":\"212\",\"En\":\"212\"},\"Direction\":0,\"EstimateTime\":1579,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE40675\",\"StopID\":\"40675\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10962\",\"RouteID\":\"10962\",\"RouteName\":{\"Zh_tw\":\"262區\",\"En\":\"262Shuttle\"},\"Direction\":0,\"EstimateTime\":570,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE10179\",\"StopID\":\"10179\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE11411\",\"RouteID\":\"11411\",\"RouteName\":{\"Zh_tw\":\"299\",\"En\":\"299\"},\"Direction\":0,\"EstimateTime\":341,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE36089\",\"StopID\":\"36089\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"National Taipei U. of Technology(Zhongxiao)\"},\"RouteUID\":\"TPE10417\",\"RouteID\":\"10417\",\"RouteName\":{\"Zh_tw\":\"忠孝幹線\",\"En\":\"Zhongxiao Metro Bus\"},\"Direction\":1,\"EstimateTime\":314,\"StopStatus\":0,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"},{\"StopUID\":\"TPE46125\",\"StopID\":\"46125\",\"StopName\":{\"Zh_tw\":\"臺北科技大學(忠孝)\",\"En\":\"Taipei university of Technology (MRT Zhongxiao Xinsheng )\"},\"RouteUID\":\"TPE15518\",\"RouteID\":\"15518\",\"RouteName\":{\"Zh_tw\":\"605新台五\",\"En\":\"605Xintaiwu\"},\"Direction\":1,\"StopStatus\":3,\"SrcUpdateTime\":\"2020-12-21T19:46:40+08:00\",\"UpdateTime\":\"2020-12-21T19:46:46+08:00\"}]"
            val data: Array<Data> = Gson().fromJson(
                    requestcontent,
                    Array<Data>::class.java
            )
            for (i in data.indices) {
                if (data[i].EstimateTime != 0) println("${data[i].StopName?.Zh_tw}站;車號：${data[i].RouteName!!.Zh_tw};需再等候${data[i].EstimateTime}")
            }
            //---------------------------------------------------------------
//            val client = OkHttpClient()
//            val request = Request.Builder()
//                .url(url)
//                .header("Authorization", authorization)
//                .header("x-date", x_date)
//                .build()
//            client.newCall(request).enqueue(object : Callback {
//                override fun onResponse(response: Response?) {
//                    if (response!!.code() == 200) {
//                        if (response.body() == null) return
//                        val data: Array<Data> = Gson().fromJson(response.body().string(), Array<Data>::class.java)
//
//                        for(i in data.indices){
//                            if(data[i].EstimateTime != 0)
//                                println("${data[i].StopName?.Zh_tw}站車號：${data[i].RouteName!!.Zh_tw};需再等候${data[i].EstimateTime}")
//                        }
//                    }
//                }
//
//                override fun onFailure(request: Request?, e: IOException?) {
//                    Logger.getLogger(search_bus::class.java.name).warning("查詢失敗")
//                }
//            })
        }
    }

    fun getServerTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(calendar.time)
    }

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


internal class Data {
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

    internal inner class Name {
        var Zh_tw: String? = null
        var En: String? = null
    }
}