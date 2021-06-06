package com.predixteam.predixai.ui.article.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.predixteam.predixai.R
import com.predixteam.predixai.data.source.remote.response.ResponseItem
import com.predixteam.predixai.databinding.ActivityDetailsArticleBinding

class DetailsArticleActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val data = intent.getParcelableExtra<ResponseItem>(EXTRA_DATA) as ResponseItem
        setLayout(data)
    }

    private fun setLayout(data: ResponseItem) {
        with(binding){
            tvTitleArticle.text = data.title
            tvContentArticle.text = data.content
            Glide.with(applicationContext)
                .load(data.images)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imgArticle)
        }
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}