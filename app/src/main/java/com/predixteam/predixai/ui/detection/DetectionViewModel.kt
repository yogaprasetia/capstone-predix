package com.predixteam.predixai.ui.detection

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.predixteam.predixai.ml.MblnetPneumo
import com.predixteam.predixai.ml.ResnetTb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.lang.Exception
import java.nio.ByteBuffer

class DetectionViewModel : ViewModel(){

    fun getPneumoDetect(input: ByteBuffer, model: MblnetPneumo): String{
        var result = ""
        CoroutineScope(Dispatchers.IO).apply {

        }
        return result
    }

    fun getTbcDetect(input: ByteBuffer, model: ResnetTb): String{
        var result = ""
        CoroutineScope(Dispatchers.IO).launch {

        }
        return result
    }



}