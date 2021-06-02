package com.predixteam.predixai.ui.detection

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.predixteam.predixai.R
import com.predixteam.predixai.databinding.ActivityDetectionBinding

class DetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionBinding
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    // Disini diproses data image yang dipick dari galeri file image dalam bentuk URI
                    binding.imgUpload.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fbUpload.setOnClickListener {
            if (checkSelfPermission((Manifest.permission.READ_EXTERNAL_STORAGE)) == PackageManager.PERMISSION_DENIED){
                val permissions : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                pickFromGallery()
            }
        }
    }

    private fun pickFromGallery() {
        ImagePicker.with(this)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .cropSquare()
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickFromGallery()
                else Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object{
        private const val PERMISSION_CODE = 1001
    }
}