package com.example.linux.carros.domain

import com.example.linux.carros.domain.retrofit.CarrosAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarroServiceRetrofit {
    private const val BASE_URL = "http://livrowebservices.com.br/rest/carros/"
    private var service: CarrosAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(CarrosAPI::class.java)
    }

    // Busca os carros por tipo ( clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        val call = service.getCarros(tipo.name)
        val carros = call.execute().body()
        return carros?: listOf()
    }

    // Salva um carro
    fun save(carro: Carro): Response {
        val call = service.save(carro)
        val response = call.execute().body()
        return response?: Response.error()
    }

    // Deleta um carro
    fun delete(carro: Carro): Response{
        val call = service.delete(carro.id)
        val response = call.execute().body()
        return response?: Response.error()
    }
}

