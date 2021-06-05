package com.predixteam.predixai.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelEntity(
    val id: Int,
    val name: String,
    val disease: String,
    val details: String,
    val imageModel: String,
    val label: String
): Parcelable