package com.mlkit.sample.apicallsample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlkit.sample.apicallsample.models.ApiData
import com.mlkit.sample.apicallsample.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(var apiRepository: ApiRepository) : ViewModel() {
    private var _getLiveData = MutableLiveData<List<ApiData>>()
    var getLiveData: LiveData<List<ApiData>> = _getLiveData


    init {
        _getLiveData.value= emptyList()
        getDataFromApi()
    }


    fun getDataFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepository.getPost()
                if (response.isSuccessful){
                    response.body()?.let {
                        _getLiveData.postValue(it)
                    }
                }



            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}