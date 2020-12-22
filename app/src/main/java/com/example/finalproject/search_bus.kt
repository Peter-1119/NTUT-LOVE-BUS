package com.example.finalproject

import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ProgressBar
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
//            val result = URL("https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?&\$format=JSON").readText()
//            val itemList = JSONObject(result)
//            val parser: Parser = Parser()
//            val stringBuilder: StringBuilder = StringBuilder(result)
//            val itemList = JSONObject(result)
//            val json: JSONObject = parser(stringBuilder) as JSONObject
//            textView7.text = "https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?&\$format=JSON"

//            val url = URL("https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?&\$format=JSON")
//
//            with(url.openConnection() as HttpURLConnection) {
//                requestMethod = "GET"  // optional default is GET
//
//                textView7.text = "\nSent 'GET' request to URL : $url; Response Code : $responseCode"
//
//                inputStream.bufferedReader().use {
//                    it.lines().forEach { line ->
//                        println(line)
//                    }
//                }
//            }

            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?&\$format=JSON")
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(request: Request, e: IOException) {
                    runOnUiThread { textView7.text = e.message }
                }

                @Throws(IOException::class)
                override fun onResponse(response: Response) {
                    val resStr = response.body().string()
                    runOnUiThread { textView7.text = resStr }
                }
            })
//            val url = "https://example.com/endpoint"
//
//            val client = OkHttpClient()
//
//            val JSON = MediaType.get("application/json; charset=utf-8")
//            val body = RequestBody.create(JSON, "{\"data\":\"$data\"}")
//            val request = Request.Builder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .url(url)
//                    .post(body)
//                    .build()
//
//            val  response = client . newCall (request).execute()
//
//            println(response.request())
//            println(response.body()!!.string())
        }
    }
}

//class search_bus : AppCompatActivity() {
//    var arrayList_details:ArrayList<ColorSpace.Model> = ArrayList();
//    //OkHttpClient creates connection pool between client and server
//    val client = OkHttpClient()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        run("http://10.0.0.7:8080/jsondata/index.html")
//    }
//
//    fun run(url: String) {
//        val request = Request.Builder()
//                .url(url)
//                .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                progress.visibility = View.GONE
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                var str_response = response.body()!!.string()
//                //creating json object
//                val json_contact:JSONObject = JSONObject(str_response)
//                //creating json array
//                var jsonarray_info:JSONArray= json_contact.getJSONArray("info")
//                var i:Int = 0
//                var size:Int = jsonarray_info.length()
//                arrayList_details= ArrayList();
//                for (i in 0.. size-1) {
//                    var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(i)
//                    var model:Model= Model();
//                    model.id=json_objectdetail.getString("id")
//                    model.name=json_objectdetail.getString("name")
//                    model.email=json_objectdetail.getString("email")
//                    arrayList_details.add(model)
//                }
//
//                runOnUiThread {
//                    //stuff that updates ui
//                    val obj_adapter : CustomAdapter
//                    obj_adapter = CustomAdapter(applicationContext,arrayList_details)
//                    listView_details.adapter=obj_adapter
//                }
//                progress.visibility = View.GONE
//            }
//        })
//    }
//}