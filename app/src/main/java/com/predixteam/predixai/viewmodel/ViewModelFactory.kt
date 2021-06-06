package com.predixteam.predixai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.predixteam.predixai.data.PredixRepository
import com.predixteam.predixai.di.Injection
import com.predixteam.predixai.ui.article.ArticleViewModel

class ViewModelFactory private constructor(private val predixRepository: PredixRepository): ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideAppRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel(predixRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}