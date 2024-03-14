package com.project.group.rupp.dse.madquiz1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.madquiz1.API.ApiService
import com.project.group.rupp.dse.madquiz1.Model.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainViewModel:ViewModel() {

    private val _mainUiState = MutableLiveData<Main>()
    val mainUiState : LiveData<Main> = _mainUiState

    fun loadMain(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val main = retrofit.create<ApiService>()

        main.getMain().enqueue(object : Callback<Main>{
            override fun onResponse(call: Call<Main>, response: Response<Main>) {
                _mainUiState.value = response.body()
            }

            override fun onFailure(call: Call<Main>, t: Throwable) {

            }

        })



    }
}
