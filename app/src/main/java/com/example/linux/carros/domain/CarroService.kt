package com.example.linux.carros.domain


import com.example.linux.carros.extensions.fromJson
import com.example.linux.carros.extensions.toJson
import com.example.linux.carros.utils.HttpHelper

object CarroService {
    private const val BASE_URL = "http://livrowebservices.com.br/rest/carros"

    // Busca os carros por tipo ( clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        // Cria a url para o tipo informado
        val url = "$BASE_URL/tipo/${tipo.name}"
        // Faz a requisição GET no web service
        val json = HttpHelper.get(url)
        // Cria a lista de carros a partir do JSON
        val carros = fromJson<List<Carro>>(json)
        return carros
    }

    // Salva um carro
    fun save(carro: Carro): Response {
        // Faz o POST do JSON carro
        val json = HttpHelper.post(BASE_URL, carro.toJson())
        // Lê a resposta
        val response = fromJson<Response>(json)
        return response
    }
}

