package com.example.retrofit_ex_app.apis

import com.example.retrofit_ex_app.models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {

    @GET
    fun getAll(): Call<List<Product>> // Diamond
}