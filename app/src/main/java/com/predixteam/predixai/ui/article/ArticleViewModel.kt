package com.predixteam.predixai.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.predixteam.predixai.data.PredixRepository
import com.predixteam.predixai.data.source.remote.response.ResponseItem

class ArticleViewModel(private val predixRepository: PredixRepository): ViewModel() {

    fun getArticle(): LiveData<List<ResponseItem>> = predixRepository.getArticle()

}