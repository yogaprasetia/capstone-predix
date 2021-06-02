package com.predixteam.predixai.ui.home

import androidx.lifecycle.ViewModel
import com.predixteam.predixai.data.ModelEntity
import com.predixteam.predixai.utils.DataDummy

class HomeViewModel : ViewModel() {


    fun getModels(): List<ModelEntity> = DataDummy.generateDummyModel()

}