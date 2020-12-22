package com.example.finalproject

import android.content.Context
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

//class GetNetworkJson : AsyncTask<Context, Int, Int>() {
//    var string: String = ""
//    var data: String = ""
//    override fun doInBackground(vararg params: Context?): Int {
//        data = sendGetRequest()
//    }
//    fun sendGetRequest(url:String) {
//        var reqParam = URLEncoder.encode(url, "UTF-8")
//
//        val mURL = URL(reqParam)
//
//        with(mURL.openConnection() as HttpURLConnection) {
//            // optional default is GET
//            requestMethod = "GET"
//
//            println("URL : $url")
//            println("Response Code : $responseCode")
//
//            BufferedReader(InputStreamReader(inputStream)).use {
//                val response = StringBuffer()
//
//                var inputLine = it.readLine()
//                while (inputLine != null) {
//                    response.append(inputLine)
//                    inputLine = it.readLine()
//                }
//                it.close()
//                println("Response : $response")
//            }
//        }
//    }
//}