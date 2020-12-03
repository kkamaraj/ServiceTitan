package com.servicetitan.pricebooks.service.model

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BookService {


    @GET("services")
    fun getPriceBookList(
        @QueryMap queries: Map<String, String>,
        @Query("serviceTitanApiKey") apiKey: String = ST_API_KEY
    ): Single<PriceBookResponse>

    companion object {

        const val ST_API_KEY: String = "45b2c77d-332c-4aec-8595-a2a47b7e9356"
        //val currentPage = 1
        //val pageSize = 20
        val BASE_URL = "https://api.servicetitan.com/v1/"
        //val pricebookApiUrlString = "https://api.servicetitan.com/v1/services?filter.page=${currentPage}&filter.pageSize=${pageSize}&serviceTitanApiKey=${ST_API_KEY}"

        fun create(): BookService {

            val loggingInterceptor = HttpLoggingInterceptor();
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(BookService::class.java)
        }
    }
}