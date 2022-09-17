package com.zrq.nicepicturedemo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.zrq.nicepicturedemo.utils.Constants.DATABASE_NAME
import com.zrq.nicepicturedemo.utils.Constants.DATABASE_VERSION
import com.zrq.nicepicturedemo.utils.Constants.FIELD_ID
import com.zrq.nicepicturedemo.utils.Constants.FIELD_PIC_URL
import com.zrq.nicepicturedemo.utils.Constants.FIELD_THUMB_URL
import com.zrq.nicepicturedemo.utils.Constants.TABLE_NAME

class PicDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table $TABLE_NAME($FIELD_ID integer primary key autoincrement," +
                "$FIELD_PIC_URL varchar(400)," +
                "$FIELD_THUMB_URL varchar(400))"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}