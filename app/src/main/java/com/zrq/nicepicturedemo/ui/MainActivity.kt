package com.zrq.nicepicturedemo.ui

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.zrq.nicepicturedemo.adapter.ViewPager2Adapter
import com.zrq.nicepicturedemo.bean.Cate
import com.zrq.nicepicturedemo.bean.Category
import com.zrq.nicepicturedemo.databinding.ActivityMainBinding
import com.zrq.nicepicturedemo.utils.Constants.GET_CATEGORY
import com.zrq.nicepicturedemo.utils.Constants.PIC_BASE_URL
import com.zrq.nicepicturedemo.utils.StatusBarUtil
import okhttp3.*
import java.io.IOException

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

    private lateinit var mBinding: ActivityMainBinding
    private val list = ArrayList<Cate>()
    private lateinit var adapter: ViewPager2Adapter
    private lateinit var animHide: ObjectAnimator

    private fun initData() {
        animHide =
            ObjectAnimator.ofFloat(mBinding.tvCategory, "alpha", 1f, 0f).apply { duration = 1000 }
        adapter = ViewPager2Adapter(this, list)
        mBinding.viewPager2.adapter = adapter
        load()
    }

    private fun initEvent() {
        animHide.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                mBinding.tvCategory.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                mBinding.tvCategory.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        mBinding.fabLove.setOnClickListener {
            startActivity(Intent(this, LoveActivity::class.java))
        }
        mBinding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                mBinding.tvCategory.alpha = 1f
                mBinding.tvCategory.visibility = View.VISIBLE
                if (positionOffset < 0.5) {
                    mBinding.tvCategory.text = list[position].name
                } else {
                    mBinding.tvCategory.text = list[position + 1].name
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d(TAG, "onPageScrollStateChanged: $state")
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    animHide.start()
                } else {
                    animHide.pause()
                }
            }
        })
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

    private fun load() {
        val url = PIC_BASE_URL + GET_CATEGORY
        val request: Request = Request.Builder()
            .url(url)
            .method("GET", null)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val string = response.body?.string()
                    val result = Gson().fromJson(string, Category::class.java)
                    list.clear()
                    if (result != null && result.res != null && result.res.category != null) {
                        result.res.category.forEach {
                            list.add(Cate(it.name, it.id))
                        }
                        runOnUiThread {
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    companion object {
        private const val PERMISSION_CODE = 1

        private const val TAG = "MainActivity"

        private val PERMISSION_ARRAY = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}