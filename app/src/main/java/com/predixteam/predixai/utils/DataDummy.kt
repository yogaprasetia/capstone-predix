package com.predixteam.predixai.utils

import com.predixteam.predixai.data.ModelEntity

object DataDummy {
    fun generateDummyModel(): List<ModelEntity>{
        val models = ArrayList<ModelEntity>()
        models.add(
            ModelEntity(
                "Model 01",
                "TBC",
                "file:///android_asset/models/placeholder2.jpg"
            )
        )

        models.add(
            ModelEntity(
                "Model 02",
                "Pneuomonia",
                "file:///android_asset/models/placeholder2.jpg"
            )
        )

        models.add(
            ModelEntity(
                "Model 03",
                "Penyakit 3",
                "file:///android_asset/models/placeholder2.jpg"
            )
        )
        return models
    }
}