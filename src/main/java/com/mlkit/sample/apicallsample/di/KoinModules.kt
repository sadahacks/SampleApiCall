package com.mlkit.sample.apicallsample.di

import android.app.Application
import com.mlkit.sample.apicallsample.api.ApiInterface
import com.mlkit.sample.apicallsample.constants.ApiConstants.BASE_URL
import com.mlkit.sample.apicallsample.constants.ApiConstants.CACHE_CONTROL_HEADER
import com.mlkit.sample.apicallsample.constants.ApiConstants.CACHE_CONTROL_NO_CACHE
import com.mlkit.sample.apicallsample.repository.ApiRepository
import com.mlkit.sample.apicallsample.ui.MainActivityViewModel
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB


val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
}


val repositoryModule = module {
    fun provideHttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    fun createApi(factory: GsonConverterFactory, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
            .create(ApiInterface::class.java)


    single { provideHttpClient(get()) }
    single { GsonConverterFactory.create() }
    single { createApi(get(), get()) }
    single { ApiRepository(get()) }
    single { httpCache(this.androidApplication()) }

}

private fun httpCache(application: Application): Cache {
    return Cache(application.applicationContext.cacheDir, CACHE_SIZE)
}


class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
        if (!shouldUseCache) return originalResponse

        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.MINUTES)
            .build()

        return originalResponse.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}