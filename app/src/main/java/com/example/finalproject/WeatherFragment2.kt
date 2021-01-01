package com.example.finalproject


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import java.util.*

class WeatherFragment2 : Fragment() {
    var weatherFont: Typeface? = null
    var currentTemperatureField: TextView? = null
    var weatherIcon: TextView? = null
    var handler: Handler

    var h : Int? =null
    var m : Int? =null
    var s : Int? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_main, container, false)
        currentTemperatureField = rootView.findViewById<View>(R.id.tem) as TextView
        weatherIcon = rootView.findViewById<View>(R.id.main) as TextView

        //標題轉圈圈
        val translationY = ObjectAnimator.ofFloat(rootView.findViewById(R.id.topic), "translationY", 100f, 100f)
        val ra = ObjectAnimator.ofFloat(rootView.findViewById(R.id.topic), "rotation", 0f, 720.0f)
        val animatorSet = AnimatorSet() //组合动画
        animatorSet.playTogether(translationY, ra) //设置动画
        animatorSet.duration = 2000
        animatorSet.start()

        weatherIcon!!.setTypeface(weatherFont)


        Thread {
            while(true){
                h = MyService().h
                m = MyService().m
                s = MyService().s
                //TODO:Accessibility content change on non-UI thread.
                //time.text = String.format("目前時刻\n%02d:%02d:%02d",h, m, s)
                Thread.sleep(1000)
            }
        }.start()

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherFont = Typeface.createFromAsset(activity!!.assets, "fonts/weather.ttf")
        updateWeatherData(CityPreference(activity!!).city)
    }

    private fun updateWeatherData(city: String?) {
        object : Thread() {
            override fun run() {
                val json = getJSON(activity!!, city)
                if (json == null) {
                    handler.post {
                        Toast.makeText(activity,
                            activity!!.getString(R.string.place_not_found),
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    handler.post { renderWeather(json) }
                }
            }
        }.start()
    }

    private fun renderWeather(json: JSONObject) {
        try {
            val details = json.getJSONArray("weather").getJSONObject(0)
            val main = json.getJSONObject("main")
            currentTemperatureField!!.text = String.format("%.1f", main.getDouble("temp")) + " ℃"
            setWeatherIcon(details.getInt("id"),
                json.getJSONObject("sys").getLong("sunrise") * 1000,
                json.getJSONObject("sys").getLong("sunset") * 1000)
        } catch (e: Exception) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data")
        }
    }

    private fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long) {
        val id = actualId / 100
        var icon = ""
        if (actualId == 800) {
            val currentTime = Date().time
            icon = if (currentTime >= sunrise && currentTime < sunset) {
                activity!!.getString(R.string.weather_sunny)
            } else {
                activity!!.getString(R.string.weather_clear_night)
            }
        } else {
            when (id) {
                2 -> icon = activity!!.getString(R.string.weather_thunder)
                3 -> icon = activity!!.getString(R.string.weather_drizzle)
                7 -> icon = activity!!.getString(R.string.weather_foggy)
                8 -> icon = activity!!.getString(R.string.weather_cloudy)
                6 -> icon = activity!!.getString(R.string.weather_snowy)
                5 -> icon = activity!!.getString(R.string.weather_rainy)
            }
        }
        weatherIcon!!.text = icon
    }

    init {
        handler = Handler()
    }
}