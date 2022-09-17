package com.zrq.nicepicturedemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zrq.nicepicturedemo.bean.Cate
import com.zrq.nicepicturedemo.ui.PicFragment

class ViewPager2Adapter (fragmentActivity:FragmentActivity,var list: ArrayList<Cate>):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return PicFragment.newInstance(list[position].id)
    }
}