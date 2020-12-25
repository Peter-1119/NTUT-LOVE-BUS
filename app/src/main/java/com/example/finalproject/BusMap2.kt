package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bus_stop2.*

class BusMap2 : AppCompatActivity() {
    val stop1= intArrayOf(R.drawable.img202,R.drawable.img262,R.drawable.img212,R.drawable.img299,R.drawable.img600,R.drawable.img605,R.drawable.img953,R.drawable.img_zh)
    //val stop1= intArrayOf(R.drawable.img202,R.drawable.img262,R.drawable.img212,R.drawable.img299,R.drawable.img600,R.drawable.img605,R.drawable.img953,R.drawable.imgZhongHsau)
    val stop2= intArrayOf(R.drawable.img298,R.drawable.img919,R.drawable.img_r57)
    val stop3_1= intArrayOf(R.drawable.img72,R.drawable.img109,R.drawable.img214,R.drawable.img222,R.drawable.img280,R.drawable.img505)
    val stop34_2= intArrayOf(R.drawable.img643,R.drawable.img668,R.drawable.img672,R.drawable.img675,R.drawable.img676,R.drawable.img680)
    val stop34_3= intArrayOf(R.drawable.img1550,R.drawable.img232)
    val stop5= intArrayOf(R.drawable.img669)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_stop2)

        showSpinner2()
        Toast.makeText(this@BusMap2,"登入公車發車時課表", Toast.LENGTH_SHORT).show()
        btn_back2.setOnClickListener {
            finish()
        }
    }

    fun showSpinner2(){
        val lunch2 = arrayListOf("★台北科技大學站(忠孝)★","202","262","212","299","600","605","953","忠孝幹線","★台北科技大學站(建國)★","298","919","紅57","★忠孝新生捷運站★\t★光華商場★","72","109","214","222","280","505","643","668","672","675","676","680","1550","松江新生幹線","★懷生國宅★","669")
        val adapter2= ArrayAdapter(this@BusMap2,android.R.layout.simple_spinner_item,lunch2)
        spinner2.adapter=adapter2
        Log.e("debug", spinner2.childCount.toString())

        //加入事件並追蹤
        spinner2.onItemSelectedListener=object:  AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val spinner_val2 = position //追蹤使用者搜尋哪個spinner 藉此知道我們要顯示甚麼 表顯該有的功能
                //選擇spinner內的元件
                when(spinner_val2) {
                    0 -> {
                        Toast.makeText(this@BusMap2, "請選擇下列公車號碼", Toast.LENGTH_SHORT).show()
                        tv_showTime.setText("從正校門出發大約2分鐘")
                    }
                    1 -> {
                        Toast.makeText(this@BusMap2, "選擇公車號:202", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[0])
                    }
                    2 -> {
                        Toast.makeText(this@BusMap2, "選擇公車號:262", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[1])
                    }
                    3 -> {
                        Toast.makeText(this@BusMap2, "選擇公車號:212", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[2])
                    }
                    4 ->{
                        Toast.makeText(this@BusMap2, "選擇公車號:299", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[3])
                    }
                    5->{
                        Toast.makeText(this@BusMap2, "選擇公車號:600", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[4])
                    }
                    6->{
                        Toast.makeText(this@BusMap2, "選擇公車號:605", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[5])
                    }
                    7->{
                        Toast.makeText(this@BusMap2, "選擇公車號:953", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[6])
                    }
                    8->{
                        Toast.makeText(this@BusMap2, "選擇公車號:忠孝新生幹線", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop1[7])
                    }
                    9->{
                        Toast.makeText(this@BusMap2, "請選擇下列公車號碼", Toast.LENGTH_SHORT).show()
                        tv_showTime.setText("從建國側門步行大約1分鐘")
                    }
                    10->{
                        Toast.makeText(this@BusMap2, "選擇公車號:298", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop2[0])
                    }
                    11->{
                        Toast.makeText(this@BusMap2, "選擇公車號:919", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop2[1])
                    }
                    12->{
                        Toast.makeText(this@BusMap2, "選擇公車號:紅57", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop2[2])
                    }
                    13->{
                        Toast.makeText(this@BusMap2, "請選擇下列公車號碼", Toast.LENGTH_SHORT).show()
                        tv_showTime.setText("★忠孝新生捷運站★:\n新生側門(接近設計館)步行大約4分鐘 \n★光華商場★:\n新生側門(接近光化館):步行大約3分鐘")
                    }
                    14->{
                        Toast.makeText(this@BusMap2, "選擇公車號:72", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop3_1[0])
                    }
                    15->{
                        Toast.makeText(this@BusMap2, "選擇公車號:109", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop3_1[1])
                    }
                    16->{
                        Toast.makeText(this@BusMap2, "選擇公車號:214", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop3_1[2])
                    }
                    17->{
                        Toast.makeText(this@BusMap2, "選擇公車號:222", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop3_1[3])
                    }
                    18->{
                        Toast.makeText(this@BusMap2, "選擇公車號:280", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop3_1[4])
                    }
                    19->{
                        Toast.makeText(this@BusMap2, "選擇公車號:505", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop3_1[5])
                    }
                    20->{
                        Toast.makeText(this@BusMap2, "選擇公車號:643", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_2[0])
                    }
                    21->{
                        Toast.makeText(this@BusMap2, "選擇公車號:668", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_2[1])
                    }
                    22->{
                        Toast.makeText(this@BusMap2, "選擇公車號:672", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_2[2])
                    }
                    23->{
                        Toast.makeText(this@BusMap2, "選擇公車號:675", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_2[3])
                    }
                    24->{
                        Toast.makeText(this@BusMap2, "選擇公車號:676", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_2[4])
                    }
                    25->{
                        Toast.makeText(this@BusMap2, "選擇公車號:680", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_2[5])
                    }
                    26->{
                        Toast.makeText(this@BusMap2, "選擇公車號:1550", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_3[0])
                    }
                    27->{
                        Toast.makeText(this@BusMap2, "選擇公車號:松山新生幹線", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop34_3[1])
                    }
                    28->{
                        Toast.makeText(this@BusMap2, "請選擇下列公車號碼", Toast.LENGTH_SHORT).show()
                        tv_showTime.setText("從建國側門步行大約4分鐘")
                    }
                    29->{
                        Toast.makeText(this@BusMap2, "選擇公車號:669", Toast.LENGTH_SHORT).show()
                        imageView2.setImageResource(stop5[0])
                    }
                }
            }

            //若無選擇spinner內的元件
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@BusMap2,"尚未選擇",Toast.LENGTH_LONG).show()
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
}