package com.zrq.nicepicturedemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.tencent.mmkv.MMKV
import com.zrq.nicepicturedemo.adapter.OnItemClickListener
import com.zrq.nicepicturedemo.adapter.PicAdapter
import com.zrq.nicepicturedemo.bean.Pic
import com.zrq.nicepicturedemo.dao.PicDaoImpl
import com.zrq.nicepicturedemo.databinding.ActivityLoveBinding
import com.zrq.nicepicturedemo.db.PicDatabaseHelper
import com.zrq.nicepicturedemo.utils.StatusBarUtil

class LoveActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoveBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        StatusBarUtil.transparencyBar(this)
        initData()
        initEvent()
    }

    private lateinit var mBinding: ActivityLoveBinding
    private lateinit var picDaoImpl: PicDaoImpl
    private lateinit var list: ArrayList<Pic>
    private lateinit var mAdapter: PicAdapter
    private lateinit var mLayoutManager: GridLayoutManager

    private fun initData() {
        MMKV.initialize(this)
        picDaoImpl = PicDaoImpl(PicDatabaseHelper(this))
        list = picDaoImpl.listAllPics()
        list.forEach {
            Log.d(TAG, "initData: $it")
        }
        mAdapter = PicAdapter(this, list, this)
        mLayoutManager = GridLayoutManager(this, 2)
        mBinding.recyclerViewLove.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun initEvent() {
        mBinding.fabHome.setOnClickListener {
            mBinding.fabHome.hide()
            finish()
        }
    }

    companion object {
        const val TAG = "LoveActivity"
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(this, PicActivity::class.java)
        intent.putExtra("picUrl", list[position].picUrl)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLongClick(view: View, position: Int): Boolean {
        picDaoImpl.deletePicById(list[position].id)
        list.removeAt(position)
        if (list.size == 1) {
            mAdapter.notifyDataSetChanged()
        } else {
            mAdapter.notifyItemRemoved(position)
        }
        Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show()
        return true
    }
}