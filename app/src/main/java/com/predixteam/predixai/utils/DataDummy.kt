package com.predixteam.predixai.utils

import com.predixteam.predixai.data.ModelEntity

object DataDummy {
    fun generateDummyModel(): List<ModelEntity>{
        val models = ArrayList<ModelEntity>()
        models.add(
            ModelEntity(
                1,
                "Model 01",
                "Pneumonia",
                "Pneumonia is an inflammatory condition of the lung primarily affecting the small air sacs known as alveoli. Symptoms typically include some combination of productive or dry cough, chest pain, fever and difficulty breathing. The severity of the condition is variable",
                "file:///android_asset/models/pneumonia.jpg",
                "pneumonia.txt"
            )
        )

        models.add(
            ModelEntity(
                2,
                "Model 02",
                "Tuberculosis",
                "Tuberculosis (TB) is an infectious disease usually caused by Mycobacterium tuberculosis (MTB) bacteria. Tuberculosis generally affects the lungs, but can also affect other parts of the body. Most infections show no symptoms, in which case it is known as latent tuberculosis. About 10% of latent infections progress to active disease which, if left untreated, kills about half of those affected.",
                "file:///android_asset/models/tuberculosis.jpg",
            "tbc.txt"
            )
        )

        models.add(
            ModelEntity(
                3,
                "Model 03",
                "Covid 19",
                "Coronavirus disease 2019 (COVID-19) is a contagious disease caused by severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2). The first known case was identified in Wuhan, China in December 2019. The disease has since spread worldwide, leading to an ongoing pandemic.",
                "file:///android_asset/models/covid19.jpg",
            "covid.txt"
            )
        )

        return models
    }
}