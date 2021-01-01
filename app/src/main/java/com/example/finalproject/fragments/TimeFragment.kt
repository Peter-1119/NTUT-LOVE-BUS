package com.example.finalproject.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.fragment.app.Fragment
import com.example.finalproject.*
import com.example.finalproject.MainActivity.Companion.dbrw
import java.util.*

class TimeFragment : Fragment() {
    private val busDetailImage = mapOf("202" to R.drawable.img202,
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
        val status = view.findViewById<TextView>(R.id.time_bus_remain)
        status.text=data?.getString("StatusName")

        var timeDeparture = view.findViewById<TextView>(R.id.time_departure)
        if (data!!.getInt("Status")!=0){
            timeDeparture.text = ""
        }else{
            val calender = Calendar.getInstance()
            val timeDeparture = view.findViewById<TextView>(R.id.time_departure)
            //超過時間範圍進未處理 MIN>60
            var hour = calender.get(Calendar.HOUR_OF_DAY)
            var min = calender.get(Calendar.MINUTE)+ data!!.getInt("TIME")-3
            if (min>=60){
                hour++
                min -= 60
            }
            val minStr=if (min<10) "0$min" else "$min"
            timeDeparture.text = String.format("若你想搭這班公車，請約%d:%s出發", hour, minStr)
        }

        var btnFavorite=view.findViewById<ImageFilterButton>(R.id.btn_favorite)
        var busDirStop = "${data?.getString("BusName")} ${data?.getString("Dir")} ${data?.getString("Stop")}"
        val c = dbrw.rawQuery("SELECT * FROM myTable WHERE busDirStop LIKE '${busDirStop}'",null)
        var favoriteEd = c.count>=1
        updateFavorite(view,favoriteEd)

        btnFavorite.setOnClickListener {
            if (favoriteEd){
                try{
                    dbrw.execSQL("DELETE FROM myTable WHERE busDirStop LIKE '${busDirStop}'")
                    Log.d("Test","刪除${busDirStop}")
                    favoriteEd=false
                    updateFavorite(view,favoriteEd)
                }catch (e: Exception){
                    Log.d("Test","刪除失敗:$e")
                }
            }else{
                try{
                    var bus = data.getString("BusName")!!
                    var dir=data.getInt("Dir")!!
                    var stop=data.getString("Stop")!!
                    var status=data.getString("StatusName")
                    dbrw.execSQL("INSERT INTO myTable(busDir,bus,dir,stop,status) VALUES(?)",
                            arrayOf<Any?>(busDirStop,bus,dir,stop,status))
                    Log.d("Test","新增${busDirStop}")
                    favoriteEd=true
                    updateFavorite(view,favoriteEd)
                }catch (e: Exception){
                    Log.d("Test","新增失敗:$e")
                }
            }
        }
        //顯示路線圖
        var btnDetail = view.findViewById<Button>(R.id.but_detail)
        btnDetail.setOnClickListener {
            var imageDialog = ImageDialog(view.context)
            imageDialog.setImage(busDetailImage[data?.getString("BusName")]).show()
            imageDialog.setCanceledOnTouchOutside(true)
            imageDialog.setCancelable(true)
        }
        //預約通知功能
        var btnReservation = view.findViewById<Button>(R.id.but_reservation)
        if (data!!.getInt("Status")!=0)
            btnReservation.visibility = View.INVISIBLE
        else{
            btnReservation.visibility = View.VISIBLE
            btnReservation.setOnClickListener {
                val calender = Calendar.getInstance()
                calender.add(Calendar.MINUTE,data?.getInt("TIME"))
                add_alarm(view.context,calender)
                setToast("成功預約提醒該公車!")
            }
        }
    }

    fun updateFavorite(view:View,favoriteEd:Boolean){
        var btnFavorite=view.findViewById<ImageFilterButton>(R.id.btn_favorite)
        var textView3=view.findViewById<TextView>(R.id.textView3)
        if (favoriteEd){
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            textView3.text = "已經加入我的最愛"
        }else{
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            textView3.text = "點擊愛心加入我的最愛"
        }
    }
    private fun setToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
