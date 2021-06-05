package com.predixteam.predixai.ui.detection

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.predixteam.predixai.R
import com.predixteam.predixai.data.ModelEntity
import com.predixteam.predixai.databinding.ActivityDetectionBinding
import com.predixteam.predixai.ml.MblnetPneumo
import com.predixteam.predixai.ml.ResnetTb
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionBinding
    private var img: Bitmap? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    binding.imgUpload.setImageURI(fileUri)
                    binding.btnDetect.isEnabled = true
                    img = if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(this.contentResolver, fileUri)
                        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                            decoder.isMutableRequired = true
                        }

                    }else{
                        MediaStore.Images.Media.getBitmap(this.contentResolver, fileUri)
                    }
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
        setSupportActionBar(binding.toolbar)

        val data = intent.getParcelableExtra<ModelEntity>(EXTRA_DATA) as ModelEntity


        binding.fbUpload.setOnClickListener {
            if (checkSelfPermission((Manifest.permission.READ_EXTERNAL_STORAGE)) == PackageManager.PERMISSION_DENIED){
                val permissions : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                pickFromGallery()
            }
        }

        binding.btnDetect.setOnClickListener {
            val input = normalizeBitmap(img!!)
            when (data.id) {
                1 -> {
                    try {
                        val model = MblnetPneumo.newInstance(this)
                        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                        inputFeature0.loadBuffer(input)

                        val outputs = model.process(inputFeature0)
                        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                        val result = getMax(outputFeature0.floatArray).toString()
                        binding.tvResultDetect.text = result
                        model.close()

                    }catch (e: Exception){
                        Log.e("Error : ", e.toString())
                    }
                }
                2 -> {
                    try {
                        val model = ResnetTb.newInstance(this)
                        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                        inputFeature0.loadBuffer(input)

                        val outputs = model.process(inputFeature0)
                        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                        val result = getMax(outputFeature0.floatArray).toString()
                        binding.tvResultDetect.text = result
                        model.close()

                    }catch (e: Exception){
                        Log.e("Error : ", e.toString())
                    }

                }
                3 -> {
                    Toast.makeText(this, "Fitur sedang maintenance", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }



    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f
        for (i in 0..1){
            if (arr[i]>min){
                ind = i
                min = arr[i]
            }
        }
        return ind
    }


    private fun normalizeBitmap(img: Bitmap): ByteBuffer {
        val imgInput = Bitmap.createScaledBitmap(img, 224, 224, true)
        val input = ByteBuffer.allocateDirect(224*224*3*4).order(ByteOrder.nativeOrder())
        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val px = imgInput.getPixel(x, y)

                val r = Color.red(px)
                val g = Color.green(px)
                val b = Color.blue(px)

                val rf = (r - 127) / 255f
                val gf = (g - 127) / 255f
                val bf = (b - 127) / 255f

                input.putFloat(rf)
                input.putFloat(gf)
                input.putFloat(bf)
            }
        }
        return input
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
        const val EXTRA_DATA = "extra_data"
    }
}