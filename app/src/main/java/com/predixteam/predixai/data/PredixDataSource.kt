package com.predixteam.predixai.data

import androidx.lifecycle.LiveData
import com.predixteam.predixai.data.source.remote.response.ResponseItem

interface PredixDataSource {

    fun getArticle(): LiveData<List<ResponseItem>>

}