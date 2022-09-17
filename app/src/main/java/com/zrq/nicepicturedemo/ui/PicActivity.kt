package com.zrq.nicepicturedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zrq.nicepicturedemo.vm.MainViewModel
import com.zrq.nicepicturedemo.R
import com.zrq.nicepicturedemo.databinding.ActivityPicBinding
import com.zrq.nicepicturedemo.utils.ImageUtil
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
    lateinit var mainModel: MainViewModel
    var picUrl = ""

    private fun initData() {
        mainModel = ViewModelProvider(this).get(MainViewModel::class.java)
        picUrl = intent.getStringExtra("picUrl").toString()
        Glide.with(this)
            .load(picUrl)
            .error(R.mipmap.loading_error)
            .into(mBinding.ivBigImg)
    }

    private fun initEvent() {
        mBinding.fabHome.setOnClickListener {
            mBinding.fabHome.hide()
            finish()
        }
        mBinding.btnDownload.setOnClickListener {
            ImageUtil.saveImage(this, picUrl, mBinding.btnDownload)
        }
    }
}