package com.example.finalproject
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.finalproject.MainActivity.Companion.dbrw


//Implement ViewHolder
class FavoriteListAdapter constructor(private val layout: Int, private val data: ArrayList<Item>)
    : BaseAdapter() {
    //ViewHolder類別，用來緩存畫面中的元件
    private class ViewHolder(v: View) {
        val busDir: TextView = v.findViewById(R.id.busDir)
        val stop: TextView = v.findViewById(R.id.stop)
        val remainTime: TextView = v.findViewById(R.id.remainTime)
        val but: Button = v.findViewById(R.id.mBtn)
    }
    //回傳項目筆數
    override fun getCount() = data.size
    //回傳某筆項目
    override fun getItem(position: Int) = data[position]
    //回傳某筆項目Id
    override fun getItemId(position: Int) = 0L
    //取得畫面
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View  //由於convertView無法賦值，必須額外建立一個View物件
        val holder: ViewHolder  //宣告ViewHolder

        if(convertView==null){
            //建立畫面
            view = View.inflate(parent.context, layout, null)
            //建立ViewHolder
            holder = ViewHolder(view)
            //將ViewHolder作為View的Tag
            view.tag = holder
        }else{
            //從Tag取得ViewHolder
            holder = convertView.tag as ViewHolder
            //取得畫面
            view = convertView
        }
        //根據position顯示圖片與名稱
        holder.busDir.text=data[position].busDirName
        holder.stop.text=data[position].stop
        holder.remainTime.text=data[position].status
        holder.but.setOnClickListener {
            try{
                //從myTable資料表刪除book欄位為輸入字串（ed_book）的資料
                var busDirStop="${data[position].busDirStop}"
                dbrw.execSQL("DELETE FROM myTable WHERE busDirStop LIKE '${busDirStop}'")
                Log.d("Test","刪除${busDirStop}")
            }catch (e: Exception){
                Log.d("Test","刪除失敗:$e")
            }finally {
                Favorite.refresh(false)
            }
        }
        return view
    }
}

data class Item(
        val busDirName: String,
        val stop:String,
        val status:String,
        val busDirStop:String
)