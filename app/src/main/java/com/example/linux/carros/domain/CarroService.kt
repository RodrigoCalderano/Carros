package com.example.linux.carros.domain

import android.content.Context

object CarroService {
    // Busca os carros por tipo ( clássicos, esportivos ou luxo)
    fun getCarros(context: Context, tipo: TipoCarro): List<Carro> {
        val tipoString = context.getString(tipo.string)
        // Cria um array vazio de carros
        val carros = mutableListOf<Carro>()
        // Cria 20 carros
        for (i in 1..20) {
            val carro = Carro()
            // Nome do carro dinânimo para brincar
            carro.nome = "Carro $tipoString: $i"
            carro.desc = "Desc $i"
            // URL da Foto fixar por enquanto
            carro.urlFoto = "http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png"
            carros.add(carro)
        }
        return carros
    }
}
