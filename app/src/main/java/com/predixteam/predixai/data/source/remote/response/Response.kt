package com.predixteam.predixai.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Response(

	@field:SerializedName("Response")
	val response: List<ResponseItem>
)

@Parcelize
data class ResponseItem(

	@field:SerializedName("images")
	val images: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String

):Parcelable
