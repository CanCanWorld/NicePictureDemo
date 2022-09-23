package com.zrq.nicepicturedemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zrq.nicepicturedemo.bean.Pic
import com.zrq.nicepicturedemo.ui.CheckPicFragment

class MyViewPagerAdapter(
    var list: ArrayList<Pic>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return CheckPicFragment.getInstance(list[position].picUrl)
    }
}