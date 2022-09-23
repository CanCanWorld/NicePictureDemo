package com.zrq.nicepicturedemo.ui

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.zrq.nicepicturedemo.R
import com.zrq.nicepicturedemo.databinding.FragmentCheckPicBinding
import com.zrq.nicepicturedemo.utils.ImageUtil
import com.zrq.nicepicturedemo.view.Loading

class CheckPicFragment(private var picUrl: String) : BaseFragment<FragmentCheckPicBinding>() {
    override fun providedViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckPicBinding {
        return FragmentCheckPicBinding.inflate(inflater, container, false)
    }

    private lateinit var loading: Loading


    override fun initData() {
        loading = Loading(requireContext())
        loadPic()
    }

    override fun initEvent() {

        mBinding.fabHome.setOnClickListener {
            mBinding.fabHome.hide()
            requireActivity().finish()
        }
        mBinding.btnDownload.setOnClickListener {
            ImageUtil.saveImage(requireContext(), picUrl, mBinding.btnDownload)
        }
    }

    private fun loadPic() {
        Glide.with(this)
            .load(picUrl)
            .error(R.mipmap.loading_error)
            .into(object : ImageViewTarget<Drawable>(mBinding.ivBigImg) {

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    Log.d(PicActivity.TAG, "onLoadStarted: ")
                    loading.show()
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    super.onResourceReady(resource, transition)
                    Log.d(PicActivity.TAG, "onResourceReady: ")
                }

                override fun setResource(resource: Drawable?) {
                    mBinding.ivBigImg.setImageDrawable(resource)
                    loading.dismiss()
                }

            })
    }

    companion object {
        fun getInstance(picUrl: String) = CheckPicFragment(picUrl)
        const val TAG = "CheckPicFragment"
    }
}
