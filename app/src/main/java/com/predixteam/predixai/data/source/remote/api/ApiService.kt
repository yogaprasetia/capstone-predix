package com.predixteam.predixai.data.source.remote.api


import com.predixteam.predixai.data.source.remote.response.ResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api")
    fun getArticle(): Call<List<ResponseItem>>

}