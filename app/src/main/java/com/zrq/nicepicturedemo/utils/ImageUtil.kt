package com.zrq.nicepicturedemo.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object ImageUtil {

    private const val TAG = "ImageUtil"
    private val handler = Handler(Looper.getMainLooper())

    fun saveImage(ctx: Context, url: String, btn: Button) {
        var bitmap: Bitmap? = null
        Thread {
            var picUrl: URL? = null
            try {
                picUrl = URL(url)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (picUrl != null) {
                var inputStream: InputStream? = null
                try {
                    val connect: HttpURLConnection = picUrl.openConnection() as HttpURLConnection
                    connect.doInput = true
                    connect.connect()
                    inputStream = connect.inputStream
                    bitmap = BitmapFactory.decodeStream(inputStream)
                } catch (e: IOException) {
                    e.printStackTrace()
                    ToastUtil.showShortTip(ctx, "图片保存失败: error3")
                } finally {
                    inputStream?.close()
                }
            }

            if (bitmap != null) {
                val sdDir = ctx.getExternalFilesDir(null)
                val filePath =
                    sdDir!!.absolutePath + File.separator + "nice_pic"
                Log.d(TAG, "saveImage: $filePath")
                val appDir = File(filePath)
                Log.d(TAG, "saveImage: ${appDir.exists()}")
                if (!appDir.exists()) {
                    val mkdir = appDir.mkdir()
                    Log.d(TAG, "saveImage: $mkdir")
                }
                val fileName = "lsp.jpg"
                val file = File(appDir, fileName)
                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(file)
                    val isSuccess = bitmap!!.compress(Bitmap.CompressFormat.JPEG, 60, fos)
                    MediaStore.Images.Media.insertImage(
                        ctx.contentResolver,
                        file.absolutePath,
                        fileName,
                        null
                    )
                    val uri = Uri.fromFile(file)
                    ctx.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                    if (isSuccess) {
                        ToastUtil.showShortTip(ctx, "图片保存成功")
                        handler.post{
                            btn.text = "已下载"
                            btn.isEnabled = false
                        }
                    } else {
                        ToastUtil.showShortTip(ctx, "图片保存失败: error1")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    ToastUtil.showShortTip(ctx, "图片保存失败: error2")
                    Log.d(TAG, "图片保存失败: error2")
                } finally {
                    fos?.flush()
                    fos?.close()
                }
            }
        }.start()
    }

}