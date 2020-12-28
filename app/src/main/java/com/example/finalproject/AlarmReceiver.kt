package com.example.finalproject

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import java.lang.String
import java.util.*


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val bData = intent.extras
        if (bData!!["title"] == "activity_app") {
            //TODO:顯示通知
            Log.d("Test", "NOTIFICATION")

            var builder : Notification.Builder
            var manager: NotificationManager
            manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("Bus", "Bus", NotificationManager.IMPORTANCE_HIGH)
                manager.createNotificationChannel(channel)
                builder = Notification.Builder(context, "Bus")
            } else {
                builder = Notification.Builder(context)
            }

            builder.setSmallIcon(R.drawable.ic_baseline_directions_bus_24)
                    .setContentTitle("注意!")
                    .setContentText("您預約的公車快到囉!")
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                            R.drawable.ic_baseline_directions_bus_24))
                    .setAutoCancel(true)
            manager.notify(0, builder.build())
        }
    }
}

/***    加入(與系統註冊)鬧鐘     */
fun add_alarm(context: Context, cal: Calendar) {
    Log.d("Test", "alarm add time: " + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND))
    val intent = Intent(context, AlarmReceiver::class.java)
    // 以日期字串組出不同的 category 以添加多個鬧鐘
    intent.addCategory("ID." + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + "-" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + "." + String.valueOf(cal.get(Calendar.MINUTE)) + "." + String.valueOf(cal.get(Calendar.SECOND)))
    val AlarmTimeTag = "Alarmtime " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND))
    intent.putExtra("title", "activity_app")
    intent.putExtra("time", AlarmTimeTag)
    val pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
    am[AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()] = pi //註冊鬧鐘
}

/***    取消(與系統註冊的)鬧鐘     */
private fun cancel_alarm(context: Context, cal: Calendar) {
    Log.d("Test", "alarm cancel time: " + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND))
    val intent = Intent(context, AlarmReceiver::class.java)
    // 以日期字串組出不同的 category 以添加多個鬧鐘
    intent.addCategory("ID." + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + "-" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + "." + String.valueOf(cal.get(Calendar.MINUTE)) + "." + String.valueOf(cal.get(Calendar.SECOND)))
    val AlarmTimeTag = "Alarmtime " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND))
    intent.putExtra("title", "activity_app")
    intent.putExtra("time", AlarmTimeTag)
    val pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
    am.cancel(pi) //取消鬧鐘，只差在這裡
}