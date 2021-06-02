package com.predixteam.predixai.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelEntity(
    val name: String,
    val disease: String,
    val imageModel: String
): Parcelable