package com.example.linux.carros.domain

import com.example.linux.carros.domain.retrofit.CarrosAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Observable


object CarroServiceRetrofit {
    private const val BASE_URL = "http://livrowebservices.com.br/rest/carros/"
    private var service: CarrosAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create(CarrosAPI::class.java)
    }

    // Busca os carros por tipo ( clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): Observable<List<Carro>> {
        // Pode retornar direto aqui, pois o Retrofit e Rx vão conversar
        return service.getCarros(tipo.name)
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

