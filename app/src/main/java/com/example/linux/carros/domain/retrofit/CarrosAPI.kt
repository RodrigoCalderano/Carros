package com.example.linux.carros.domain.retrofit

import com.example.linux.carros.domain.Carro
import com.example.linux.carros.domain.Response
import retrofit2.Call
import retrofit2.http.*


interface CarrosAPI {
    @GET("tipo/{tipo}")
    fun getCarros(@Path("tipo") tipo: String): Call<List<Carro>>
    @POST("./")
    fun save(@Body carro: Carro): Call<Response>
    @DELETE("{id}")
    fun delete(@Path("id") id: Long): Call<Response>
}