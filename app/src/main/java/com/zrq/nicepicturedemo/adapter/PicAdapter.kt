package com.zrq.nicepicturedemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zrq.nicepicturedemo.R
import com.zrq.nicepicturedemo.bean.Pic
import com.zrq.nicepicturedemo.databinding.ItemPicBinding

class PicAdapter(
    private var context: Context,
    private var list: ArrayList<Pic>?,
    private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VH<ItemPicBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemPicBinding> {
        val mBinding: ItemPicBinding =
            ItemPicBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(mBinding)
    }

    override fun onBindViewHolder(holder: VH<ItemPicBinding>, position: Int) {
        if (list != null) {
            holder.binding.apply {
                Glide.with(context)
                    .load(list!![position].thumbUrl)
                    .error(R.mipmap.loading_error)
                    .into(ivImage)
                root.setOnClickListener {
                    onItemClickListener.onClick(it, position)
                }
                root.setOnLongClickListener {
                    onItemClickListener.onLongClick(it, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}