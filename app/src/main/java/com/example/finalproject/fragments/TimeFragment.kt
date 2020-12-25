package com.example.finalproject.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.fragment.app.Fragment
import com.example.finalproject.BusInformation
import com.example.finalproject.Favorite
import com.example.finalproject.R
import java.util.*

class TimeFragment : Fragment() {
    private lateinit var view2: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view2=inflater.inflate(R.layout.fragment_time, container, false)

        return view2
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arguments
        val time = view.findViewById<TextView>(R.id.time_bus_remain)
        time.text = String.format("%d分鐘後到", data?.getInt("TIME"))
        val calender = Calendar.getInstance()
        val timeDeparture = view.findViewById<TextView>(R.id.time_departure)
        //超過時間範圍進未處理 MIN>60
        var hour = calender.get(Calendar.HOUR_OF_DAY)
        var min = calender.get(Calendar.MINUTE)+ data!!.getInt("TIME")+10
        if (min>=60){
            hour++
            min -= 60
        }
        val minStr=if (min<10) "0$min" else "$min"
        timeDeparture.text = String.format("若你想搭這班公車，請%d:%s出發", hour, minStr)
        //view==view??
        var btnFavorite=view2.findViewById<ImageFilterButton>(R.id.btn_favorite)
        btnFavorite?.setOnClickListener {
            try{
                //新增一筆book與price資料進入myTable資料表
                BusInformation.dbrw.execSQL("INSERT INTO myTable(busDir) VALUES(?)", arrayOf<Any?>("${data?.getString("BusName")}\t${data?.getString("Dir")}"))
                Log.d("Test","新增公車${data?.getString("BusName")}   方向${data?.getString("Dir")}")
            }catch (e: Exception){
                Log.d("Test","新增失敗:$e")
            }
        }
    }

}