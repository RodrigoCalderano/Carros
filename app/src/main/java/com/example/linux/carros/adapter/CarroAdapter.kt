package com.example.linux.carros.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linux.carros.R
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.extensions.loadUrl
import kotlinx.android.synthetic.main.adapter_carro.view.*

class CarroAdapter( val carros: List<Carro>, val onClick: (Carro) -> Unit) :
    RecyclerView.Adapter<CarroAdapter.CarrosViewHolder>() {

    // Retorna a quantidade de carros na lista
    override fun getItemCount() = this.carros.size

    // Infla o layout do adapter e retorna o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrosViewHolder {
        // Infla a view do adapter
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_carro, parent, false)
        // Retorna o ViewHolder que contém todas as views
        return CarrosViewHolder(view)
    }

    // Faz o bind para atualizar o valor das views com os dados do Carro
    override fun onBindViewHolder(holder: CarrosViewHolder, position: Int) {
        // Recupera o objeto carro
        val carro = carros[position]
        // Declara a variável view para facilitar o acesso abaixo
        // A view contém as variáveis definidas no XML
        val view = holder.itemView
        // With view vai substituir todos lugares que precisar de view
        with(view){
            // Atualiza os dados do carro
            tNome.text = carro.nome
            // Faz o download da foto e mostra o ProgressBar
            img.loadUrl(carro.urlFoto, view.progress)
            // Adiciona o evento de clique na linha
            setOnClickListener { onClick(carro) }
        }
    }

    // ViewHolder com as views
    // Não precisa declarar os componentes da view por causa da biblioteca synthetic
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view)
}