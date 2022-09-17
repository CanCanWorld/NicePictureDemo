package com.zrq.nicepicturedemo.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object ToastUtil {
    private val handler = Handler(Looper.getMainLooper())

    fun showShortTip(ctx: Context, msg: String) {
        handler.post {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
        }
    }
}