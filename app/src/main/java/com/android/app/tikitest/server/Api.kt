package com.android.app.tikitest.server

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    private lateinit var retrofit: Retrofit

    private fun getRetrofit(): Retrofit {
        if (!::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getApiHomeService() : HomeService {
        return getRetrofit().create(HomeService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.tiki.vn/"

        const val BANNER = "/v2/home/banners/v2"
        const val QUICK_LINK = "/shopping/v2/widgets/quick_link"
        const val FLASH_DEAL = "/v2/widget/deals/hot"

        private var INSTANCE: Api? = null
        val instance: Api
            get() = INSTANCE ?: synchronized(Api::class.java) {
                INSTANCE ?: Api().also {
                    INSTANCE = it
                }
            }
    }
}