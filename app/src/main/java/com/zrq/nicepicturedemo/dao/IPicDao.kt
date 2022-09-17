package com.zrq.nicepicturedemo.dao

import com.zrq.nicepicturedemo.bean.Pic

interface IPicDao {
    fun addPic(pic: Pic): Long

    fun deletePicById(id: Int): Int

    fun listAllPics(): ArrayList<Pic>
}