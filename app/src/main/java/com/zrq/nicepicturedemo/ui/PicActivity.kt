package com.zrq.nicepicturedemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zrq.nicepicturedemo.adapter.MyViewPagerAdapter
import com.zrq.nicepicturedemo.bean.Pic
import com.zrq.nicepicturedemo.databinding.ActivityPicBinding
import com.zrq.nicepicturedemo.utils.StatusBarUtil

class PicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPicBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        StatusBarUtil.transparencyBar(this)
        initData()
        initEvent()
    }

    lateinit var mBinding: ActivityPicBinding
    lateinit var adapter: MyViewPagerAdapter

    private fun initData() {
        val listString = intent.getStringExtra("list")
        val position = intent.getIntExtra("position", 0)
        val list: ArrayList<Pic> =
            Gson().fromJson(listString, object : TypeToken<ArrayList<Pic>>() {}.type)
        adapter = MyViewPagerAdapter(list, this)
        mBinding.viewPager2Pic.adapter = adapter
        mBinding.viewPager2Pic.setCurrentItem(position, false)
    }

    private fun initEvent() {
    }

    companion object {
        const val TAG = "PicActivity"
    }
}