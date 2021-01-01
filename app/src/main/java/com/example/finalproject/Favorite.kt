package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.MainActivity.Companion.dbrw
import kotlinx.android.synthetic.main.favorite_page.*

class Favorite : AppCompatActivity() {
    companion object{
        private val items1 = ArrayList<Item>()
        private lateinit var adapter: FavoriteListAdapter
        fun refresh(api:Boolean){
            Log.d("Tab","Refresh")
            if (api){
                updateData()
            }
            val c = dbrw.rawQuery("SELECT * FROM myTable",null)
            //從第一筆開始輸出
            c.moveToFirst()
            //清空舊資料
            items1.clear()
            for (i in 0 until c.count) {
                var busDir = "${c.getString(1)} ${c.getString(2)}"
                items1.add(Item(busDir,c.getString(3),c.getString(4)))
                //items1.add("公車:${c.getString(0)}")
                //移動到下一筆
                c.moveToNext()
            }
            //更新列表資料
            adapter.notifyDataSetChanged()
            //關閉Cursor
            c.close()
        }
        private fun updateData(){
            val c = dbrw.rawQuery("SELECT * FROM myTable",null)
            val api=SearchBus()
            var filter = ""
            c.moveToFirst()
            for (i in 0 until c.count) {
                val stop = c.getString(3)!!
                val bus=c.getString(1)!!
                val dir = c.getInt(2)!!
                if (i==0)
                    filter += "(StopName/Zh_tw eq '${stop}' and RouteName/Zh_tw eq '${bus}' and Direction eq ${dir})"
                else
                    filter += " or (StopName/Zh_tw eq '${stop}' and RouteName/Zh_tw eq '${bus}' and Direction eq ${dir})"
            }
            val data=api.getStopDataByStr(filter)!!

            c.moveToFirst()
            for (i in 0 until c.count) {
                val stop = c.getString(3)!!
                val bus=c.getString(1)!!
                val dir = c.getInt(2)!!
                val status = getStatus(data,stop, bus, dir)
                try{
                    //更新book欄位為輸入字串（ed_book）的資料的price欄位數值
                    dbrw.execSQL("UPDATE myTable SET status = '${status}' WHERE stop LIKE '${stop}' AND bus LIKE '${bus}' AND dir LIKE '${dir}'")
                }catch (e: Exception){
                    Log.d("Test","更新失敗:$e")
                }
            }
        }
        private fun getStatus(data:Array<StopID>,stop:String,bus:String,dir:Int):String{
            var index:Int?=null
            var status:String=""
            for (i in data.indices){
                if (data[i].StopName!!.Zh_tw==stop && data[i].RouteName!!.Zh_tw==bus && data[i].Direction==dir){
                    index=i
                    break
                }
            }
            if (index!=null)
                if (data[index].StopStatus==0){
                    var time = data[index].EstimateTime/60
                    if (time==0)
                        status = "即將進站"
                    else
                        status = "${time}分鐘後到"
                }
                else if (data[index].StopStatus==1)
                    status="尚未發車"
                else if (data[index].StopStatus==2)
                    status="交管不停靠"
                else if (data[index].StopStatus==3)
                    status="末班車已過"
                else
                    status="今日不營運"
            return status
        }
    }
    //private var items: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_page)
        val actionbar = supportActionBar
        actionbar!!.title = "我的最愛"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //宣告Adapter並連結ListView
        //adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //listView.adapter = adapter
        adapter= FavoriteListAdapter(R.layout.favorite_list,items1)
        listView.adapter=adapter
        refresh(true)
    }
    //Create Refresh Button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item.itemId)
        return when (item.itemId) {
            R.id.refresh -> {
                refresh(true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //Click Back Button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun showToast(text: String) =
            Toast.makeText(this,text, Toast.LENGTH_LONG).show()

}