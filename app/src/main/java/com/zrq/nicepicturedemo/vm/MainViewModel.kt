package com.zrq.nicepicturedemo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var picUrl = MutableLiveData<String>()
    var position = 0
}