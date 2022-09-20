package com.zrq.nicepicturedemo.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.zrq.nicepicturedemo.R
import com.zrq.nicepicturedemo.databinding.ActivityPicBinding
import com.zrq.nicepicturedemo.utils.ImageUtil
import com.zrq.nicepicturedemo.utils.StatusBarUtil
import com.zrq.nicepicturedemo.view.Loading
import com.zrq.nicepicturedemo.vm.MainViewModel


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
    private lateinit var loading: Loading
    var picUrl = ""

    private fun initData() {
        loading = Loading(this)
        mainModel = ViewModelProvider(this).get(MainViewModel::class.java)
        picUrl = intent.getStringExtra("picUrl").toString()
        Glide.with(this)
            .load(picUrl)
            .error(R.mipmap.loading_error)
            .into(object : ImageViewTarget<Drawable>(mBinding.ivBigImg) {

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    Log.d(TAG, "onLoadStarted: ")
                    loading.show()
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    super.onResourceReady(resource, transition)
                    Log.d(TAG, "onResourceReady: ")
                }

                override fun setResource(resource: Drawable?) {
                    mBinding.ivBigImg.setImageDrawable(resource)
                    loading.dismiss()
                }

            })

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

    companion object {
        const val TAG = "PicActivity"
    }
}