package com.devspacecinenow.detail.presentation

import android.text.Editable.Factory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel(
    private val detailService: DetailService
): ViewModel(){
    private val _uiDetail = MutableStateFlow<MovieDto?>(null)
    val uiDetail: StateFlow<MovieDto?> = _uiDetail

    fun fetchMovieDetail(movieId: String){
        if (_uiDetail.value == null){
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getMovieById(movieId)
                if (response.isSuccessful){
                    _uiDetail.value = response.body()
                }else{
                    Log.d("MovieDetailViewModel", "Request Error :: ${response.errorBody()}")
                }
            }
        }
    }

    fun cleanMovieId(){
        viewModelScope.launch {
            delay(2000)
            _uiDetail.value = null
        }
    }

    companion object{
        val FactoryDetail : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(
                    detailService
                )as T
            }
        }
    }
}