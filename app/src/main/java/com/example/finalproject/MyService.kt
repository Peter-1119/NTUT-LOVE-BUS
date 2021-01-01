package com.example.finalproject

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import java.util.*


class MyService : Service() {

    var flag = false
    val calender = Calendar.getInstance()
    //超過時間範圍進未處理 MIN>60
    var h = calender.get(Calendar.HOUR_OF_DAY) + 8
    var m = calender.get(Calendar.MINUTE)
    var s = calender.get(Calendar.SECOND)

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        throw UnsupportedOperationException("Not yet implemented");
    }
    override fun onStartCommand(intent: Intent, flags: Int, startID: Int): Int {
        flag = intent.getBooleanExtra("flag", false)
        Thread {
            while (flag) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                s++
                if (s >= 60) {
                    s = 0
                    m++
                    if (m >= 60) {
                        m = 0
                        h++
                    }
                }
                val intent = Intent("MyMessage")
                val bundle = Bundle()
                bundle.putInt("H", h)
                bundle.putInt("M", m)
                bundle.putInt("S", s)
                intent.putExtras(bundle)
                sendBroadcast(intent)
            }
        }.start()
        return START_STICKY
    }
}