package com.example.retrofitdemo

import com.example.retrofitdemo.models.DataClass
import com.example.retrofitdemo.models.DataClassItem
import com.example.retrofitdemo.models.PhotosList
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    fun getPost(): Call<DataClass>

    @GET("photos")
    fun getPhotos(): Call<PhotosList>
}