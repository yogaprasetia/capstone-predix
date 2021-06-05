package com.predixteam.predixai.ui.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.predixteam.predixai.R
import com.predixteam.predixai.data.ModelEntity
import com.predixteam.predixai.databinding.ActivityDetailsBinding
import com.predixteam.predixai.ui.detection.DetectionActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val data = intent.getParcelableExtra<ModelEntity>(EXTRA_DATA) as ModelEntity

        setLayout(data)

        binding.btnToDetection.setOnClickListener {
            val intent = Intent(this@DetailsActivity, DetectionActivity::class.java)
            intent.putExtra(DetectionActivity.EXTRA_DATA, data)
            startActivity(intent)
        }
    }

    private fun setLayout(data: ModelEntity) {
        with(binding){
            tvDisease.text = data.disease
            tvDetailsDisease.text = data.details
            Glide.with(applicationContext)
                .load(data.imageModel)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imgModel)
        }
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}