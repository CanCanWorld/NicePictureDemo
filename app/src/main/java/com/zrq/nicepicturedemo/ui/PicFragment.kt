package com.zrq.nicepicturedemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.zrq.nicepicturedemo.adapter.OnItemClickListener
import com.zrq.nicepicturedemo.adapter.PicAdapter
import com.zrq.nicepicturedemo.bean.Pic
import com.zrq.nicepicturedemo.bean.Picture
import com.zrq.nicepicturedemo.dao.PicDaoImpl
import com.zrq.nicepicturedemo.databinding.FragmentPicBinding
import com.zrq.nicepicturedemo.db.PicDatabaseHelper
import com.zrq.nicepicturedemo.utils.Constants
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class PicFragment(var category: String) : BaseFragment<FragmentPicBinding>(), OnItemClickListener {
    override fun providedViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPicBinding {
        return FragmentPicBinding.inflate(inflater, container, false)
    }

    private val list = ArrayList<Pic>()
    private lateinit var mAdapter: PicAdapter
    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var picDaoImpl: PicDaoImpl
    private lateinit var refreshLayout: RefreshLayout
    private var random = 0

    override fun initData() {
        mAdapter = PicAdapter(requireContext(), list, this)
        mLayoutManager = GridLayoutManager(context, 2)
        mBinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
        picDaoImpl = PicDaoImpl(PicDatabaseHelper(requireContext()))
        refreshLayout = mBinding.refreshLayout
        refreshLayout.setRefreshHeader(ClassicsHeader(requireContext()))
        refreshLayout.setRefreshFooter(ClassicsFooter(requireContext()))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initEvent() {
        load()
        refreshLayout.setOnRefreshListener {
            list.clear()
            load()
            mAdapter.notifyDataSetChanged()
        }
        refreshLayout.setOnLoadMoreListener {
            load()
        }
    }

    private fun load() {
        random = Random(Date().time).nextInt(200)
        val url = Constants.getPicByCategory(category, LIMIT, random)
        val request: Request = Request.Builder()
            .url(url)
            .method("GET", null)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val string = response.body?.string()
                    val result = Gson().fromJson(string, Picture::class.java)
                    if (result != null && result.res != null && result.res.vertical != null) {
                        result.res.vertical.forEach {
                            list.add(Pic(it.img, it.thumb))
                        }
                        requireActivity().runOnUiThread {
                            mAdapter.notifyItemRangeInserted(list.size, LIMIT)
                            refreshLayout.finishLoadMore()
                            refreshLayout.finishRefresh()
                        }
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "PicFragment"
        const val LIMIT = 20
        fun newInstance(category: String) = PicFragment(category)
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(requireActivity(), PicActivity::class.java)
        intent.putExtra("list", Gson().toJson(list))
        intent.putExtra("position", position)
        startActivity(intent)
    }

    override fun onLongClick(view: View, position: Int): Boolean {
        Toast.makeText(requireContext(), "已收藏", Toast.LENGTH_SHORT).show()
        picDaoImpl.addPic(list[position])
        return true
    }

    override fun onResume() {
        super.onResume()
    }

}