package com.example.finalproject

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalproject.RemoteFetch.getJSON
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.DateFormat
import java.util.*

class WeatherFragment:Fragment() {
    var weatherFont: Typeface? = null
    var hum: TextView? = null
    var feel: TextView? = null
    var pressure: TextView? = null
    var updatedField: TextView? = null
    var future: TextView? = null

    var explain: TextView?=null
    var max: TextView? = null
    var min: TextView? = null
    private var currentTemperatureField: TextView? = null
    var weatherIcon: TextView? = null
    var handler: Handler  //定義這些天氣資訊的物件並在onCreate裡面啟用他們

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_show__weather2, container, false)



        hum = rootView.findViewById<View>(R.id.hum) as TextView
        feel = rootView.findViewById<View>(R.id.feel) as TextView

        pressure = rootView.findViewById<View>(R.id.pressure) as TextView
        updatedField = rootView.findViewById<View>(R.id.textView13) as TextView
        max = rootView.findViewById<View>(R.id.max) as TextView
        min = rootView.findViewById<View>(R.id.min) as TextView
        explain = rootView.findViewById<View>(R.id.explain) as TextView
        currentTemperatureField = rootView.findViewById<View>(R.id.current_temperature_field) as TextView
        weatherIcon = rootView.findViewById<View>(R.id.weather_icon) as TextView
        weatherIcon!!.typeface = weatherFont   //設定watherIcon由我們在fonts資料夾中新增的字型來顯示

        return rootView

    }

    override fun onCreate(savedInstanceState: Bundle?) {//啟用並指定字型檔位置
        super.onCreate(savedInstanceState)
        weatherFont = Typeface.createFromAsset(activity!!.assets, "fonts/weather.ttf") //字型檔位置

        updateWeatherData(CityPreference(activity!!).city)
    }

    private fun updateWeatherData(city: String?) {//這個thread將我們從remoteFetch抓下來的getJSON做判斷，如果資料正確將啟用renderWeather的這個方法
        object : Thread() {
            override fun run() {
                val json = getJSON(activity!!, city)
                if (json == null) {//判斷有無資料
                    handler.post { //只有主要的thread可以更新UI的資訊，若是我們直接從背景的thread去更新UI會出錯，所以我們用handler.post的方法
                        Toast.makeText(activity,
                            activity!!.getString(R.string.place_not_found),
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    handler.post { renderWeather(json) }//資料無誤將啟用renderWeather的方法
                }
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun renderWeather(json: JSONObject) {//從抓取的jason去找我們的資料並顯示出來
        try {

            val details = json.getJSONArray("weather").getJSONObject(0)
            val main = json.getJSONObject("main")



            explain!!.text = details.getString("description").toUpperCase(Locale.TAIWAN)
            max!!.text=(
                    String.format("%.1f", main.getDouble("temp_max"))+ "°");
            feel!!.text =  "體感"+(main.getString("feels_like"))
            min!!.text=(
                    String.format("%.1f", main.getDouble("temp_min"))+ "°");
            hum!!.text = (main.getString("humidity"))+" %"
            pressure!!.text = (main.getString("pressure"))
            currentTemperatureField!!.text=(
                    String.format("%.1f", main.getDouble("temp"))+ "°");

            val df = DateFormat.getDateTimeInstance()
            val updatedOn = df.format(Date(json.getLong("dt")*1000 +28800000))
            updatedField!!.text = "更新時間: $updatedOn"//這裡顯示的是英國格林威治時間，台灣時間我還要在改一下
            setWeatherIcon(details.getInt("id"),
            )

        } catch (e: Exception) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data")
        }
    }

    private fun setWeatherIcon(actualId: Int) {
        val id = actualId / 100
        var icon = ""

        when (id) {
            2 -> icon = activity!!.getString(R.string.weather_thunder)
            3 -> icon = activity!!.getString(R.string.weather_drizzle)
            7 -> icon = activity!!.getString(R.string.weather_foggy)
            8 -> icon = activity!!.getString(R.string.weather_cloudy)
            6 -> icon = activity!!.getString(R.string.weather_snowy)
            5 -> icon = activity!!.getString(R.string.weather_rainy)//這裡網站上有給對應的id名稱，哪個ID對應哪個圖案
        }


        weatherIcon!!.text = icon

    }


    fun changeCity(city: String?) {//若是要更改城市時用，本專題不需要
        updateWeatherData(city)
    }

    init {
        handler = Handler()

    }
}