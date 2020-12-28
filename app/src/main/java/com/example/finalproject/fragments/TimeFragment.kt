package com.example.finalproject.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.fragment.app.Fragment
import com.example.finalproject.BusInformation
import com.example.finalproject.Favorite
import com.example.finalproject.ImageDialog
import com.example.finalproject.R
import java.util.*

class TimeFragment : Fragment() {
    val busDetailImage = mapOf("202" to R.drawable.img202,
            "262" to R.drawable.img262,
            "212" to R.drawable.img212,
            "299" to R.drawable.img299,
            "600" to R.drawable.img600,
            "605" to R.drawable.img605,
            "953" to R.drawable.img953,
            "忠孝幹線" to R.drawable.img_zh,
            "298" to R.drawable.img298,
            "919" to R.drawable.img919,
            "紅57" to R.drawable.img_r57,
            "72" to R.drawable.img72,
            "109" to R.drawable.img109,
            "214" to R.drawable.img214,
            "222" to R.drawable.img222,
            "280" to R.drawable.img280,
            "505" to R.drawable.img505,
            "643" to R.drawable.img643,
            "668" to R.drawable.img668,
            "672" to R.drawable.img672,
            "675" to R.drawable.img675,
            "676" to R.drawable.img676,
            "680" to R.drawable.img680,
            "1550" to R.drawable.img1550,
            "松江新生幹線" to R.drawable.img232)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false)
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
        var btnFavorite=view.findViewById<ImageFilterButton>(R.id.btn_favorite)
        btnFavorite.setOnClickListener {
            try{
                //新增一筆book與price資料進入myTable資料表
                BusInformation.dbrw.execSQL("INSERT INTO myTable(busDir) VALUES(?)", arrayOf<Any?>("${data?.getString("BusName")}\t${data?.getString("Dir")}"))
                Log.d("Test","新增公車${data?.getString("BusName")}   方向${data?.getString("Dir")}")
            }catch (e: Exception){
                Log.d("Test","新增失敗:$e")
            }
        }
        var btnDetail = view.findViewById<Button>(R.id.but_detail)
        btnDetail.setOnClickListener {



            var imageDialog = ImageDialog(view.context)
            imageDialog.setImage(busDetailImage[data?.getString("BusName")]).show()
            imageDialog.setCanceledOnTouchOutside(true)
            imageDialog.setCancelable(true)
        }
    }

}
