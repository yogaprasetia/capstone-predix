package com.predixteam.predixai.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.predixteam.predixai.data.source.remote.RemoteDataSource
import com.predixteam.predixai.data.source.remote.response.ResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PredixRepository (private val remoteDataSource: RemoteDataSource): PredixDataSource {

    companion object {
        @Volatile
        private var INSTANCE: PredixRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): PredixRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PredixRepository(remoteDataSource)
            }
    }

    override fun getArticle(): LiveData<List<ResponseItem>> {
        val listArticle = MutableLiveData<List<ResponseItem>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getArticle(object : RemoteDataSource.LoadArticleCallback{
                override fun onArticleReceived(articleResponse: List<ResponseItem>) {
                    val article = ArrayList<ResponseItem>()
                    for (response in articleResponse){
                        article.add(response)
                    }
                    listArticle.postValue(article)
                }

            })
        }
        return listArticle
    }

}
