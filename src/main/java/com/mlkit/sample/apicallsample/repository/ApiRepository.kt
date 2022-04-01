package com.mlkit.sample.apicallsample.repository

import com.mlkit.sample.apicallsample.api.ApiInterface

class ApiRepository(var apiInterface: ApiInterface) {
    suspend fun getPost() = apiInterface.getPost()
}