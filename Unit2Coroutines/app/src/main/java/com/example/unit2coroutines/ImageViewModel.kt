package com.example.unit2coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ImageViewModel: ViewModel() {
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    private suspend fun featchImage() {
        withContext(Dispatchers.IO){
            delay(200)
            android.util.Log.d("ImageViewModel", "Fetching new Image...")
            val url = "https://fastly.picsum.photos/id/780/500/500.jpg?hmac=RNJg26KH7blampLCo1zi5W3TOla5En8OShcm7BnRtuI$" +
                    "{System.currentTimeMillis()}"
            _imageUrl.postValue(url)
        }
    }

    fun startLiveImages() {
        viewModelScop.launch {
            featchImage()
            withContext(Dispatchers.IO){
                val url = "https://picsum.photos/500?random=${System.currentTimeMillis()}"
            }
            delay(3000)
        }
    }

}


