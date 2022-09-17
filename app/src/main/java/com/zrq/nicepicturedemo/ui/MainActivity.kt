package com.zrq.nicepicturedemo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tencent.mmkv.MMKV
import com.zrq.nicepicturedemo.databinding.ActivityMainBinding
import com.zrq.nicepicturedemo.utils.StatusBarUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        StatusBarUtil.transparencyBar(this)
        checkPermission()
        initData()
        initEvent()
    }

    private fun initData() {
        MMKV.initialize(this)
    }

    private fun initEvent() {
        mBinding.fabLove.setOnClickListener {
            startActivity(Intent(this, LoveActivity::class.java))
        }
    }

    private fun checkPermission() {
        var isRequest = true
        PERMISSION_ARRAY.forEach {
            isRequest = isRequest && (checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED)
        }
        if (isRequest) {
            requestPermissions(PERMISSION_ARRAY, PERMISSION_CODE)
        }
    }

    private lateinit var mBinding: ActivityMainBinding

    companion object {
        private const val PERMISSION_CODE = 1

        val PERMISSION_ARRAY = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}