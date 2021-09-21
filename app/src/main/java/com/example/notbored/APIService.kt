package com.example.notbored

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getActivityByCategory(@Url url:String) : Response<ActivityResponse>
}
