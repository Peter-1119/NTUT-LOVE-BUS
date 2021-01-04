package com.example.finalproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoriteSQLiteOpenHelper (context: Context): SQLiteOpenHelper(context, name, null, version) {
    companion object {
        private const val name = "mdatabase.db" //資料庫名稱
        private const val version = 1 //資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE myTable(busDirStop text PRIMARY KEY,bus text,dir integer,stop text,status text,dirName text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //刪除資料表
        db.execSQL("DROP TABLE IF EXISTS myTable")
        //重建資料庫
        onCreate(db)
    }
}