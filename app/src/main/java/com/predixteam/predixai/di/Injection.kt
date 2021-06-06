package com.predixteam.predixai.di

import com.predixteam.predixai.data.PredixRepository
import com.predixteam.predixai.data.source.remote.RemoteDataSource

object Injection {
    fun provideAppRepository(): PredixRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return PredixRepository.getInstance(remoteDataSource)
    }
}