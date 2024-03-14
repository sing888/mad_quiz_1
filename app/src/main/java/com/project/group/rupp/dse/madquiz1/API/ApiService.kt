package com.project.group.rupp.dse.madquiz1.API

import com.project.group.rupp.dse.madquiz1.Model.Main
import retrofit2.Call
import retrofit2.http.GET

interface ApiService{
    @GET("fb-profile.json")
    fun getMain(): Call<Main>
}
