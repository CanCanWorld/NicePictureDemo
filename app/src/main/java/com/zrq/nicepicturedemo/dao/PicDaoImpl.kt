package com.zrq.nicepicturedemo.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import com.zrq.nicepicturedemo.utils.Constants.FIELD_ID
import com.zrq.nicepicturedemo.utils.Constants.FIELD_PIC_URL
import com.zrq.nicepicturedemo.utils.Constants.FIELD_THUMB_URL
import com.zrq.nicepicturedemo.utils.Constants.TABLE_NAME
import com.zrq.nicepicturedemo.bean.Pic
import com.zrq.nicepicturedemo.db.PicDatabaseHelper

class PicDaoImpl(
    private var mHelper: PicDatabaseHelper
) : IPicDao {

    override fun addPic(pic: Pic): Long {
        val db = mHelper.writableDatabase
        val values = picToValues(pic)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    override fun deletePicById(id: Int): Int {
        val db = mHelper.writableDatabase
        val result = db.delete(TABLE_NAME, "$FIELD_ID=$id", null)
        db.close()
        return result
    }

    override fun listAllPics(): ArrayList<Pic> {
        val db = mHelper.writableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val pics = ArrayList<Pic>()
        while (cursor.moveToNext()) {
            pics.add(cursorToPic(cursor))
        }
        db.close()
        return pics
    }

    private fun picToValues(pic: Pic): ContentValues {
        val values = ContentValues()
        values.put(FIELD_PIC_URL, pic.picUrl)
        values.put(FIELD_THUMB_URL, pic.thumbUrl)
        return values
    }

    @SuppressLint("Range")
    private fun cursorToPic(cursor: Cursor): Pic {
        val pic = Pic()
        pic.id = cursor.getInt(cursor.getColumnIndex(FIELD_ID))
        pic.picUrl = cursor.getString(cursor.getColumnIndex(FIELD_PIC_URL))
        pic.thumbUrl = cursor.getString(cursor.getColumnIndex(FIELD_THUMB_URL))
        return pic
    }
}