package com.example.do_an_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoadingViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
}