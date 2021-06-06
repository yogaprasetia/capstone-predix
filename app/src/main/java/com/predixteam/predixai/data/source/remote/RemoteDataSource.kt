package com.predixteam.predixai.data.source.remote

import com.predixteam.predixai.data.source.remote.api.ApiConfig
import com.predixteam.predixai.data.source.remote.response.ResponseItem
import retrofit2.await

class RemoteDataSource{

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    suspend fun getArticle(
        callback: LoadArticleCallback
    ){
        ApiConfig.getApiService().getArticle().await().let {
            callback.onArticleReceived(it)
            }
        }

    interface LoadArticleCallback {

        fun onArticleReceived(articleResponse: List<ResponseItem>)
    }

}
