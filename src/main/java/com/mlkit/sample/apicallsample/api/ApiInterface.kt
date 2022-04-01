package com.mlkit.sample.apicallsample.api

import com.mlkit.sample.apicallsample.constants.ApiConstants.END_POINT
import com.mlkit.sample.apicallsample.models.ApiData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET(END_POINT)
    suspend fun getPost(): Response<List<ApiData>>
}