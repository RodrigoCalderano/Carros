package com.example.linux.carros.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.linux.carros.R
import com.example.linux.carros.domain.Carro
import com.squareup.picasso.Picasso

class CarroAdapter( val carros: List<Carro>, val onClick: (Carro) -> Unit) :
    RecyclerView.Adapter<CarroAdapter.CarrosViewHolder>() {
    // ViewHolder com as views
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tNome: TextView = view.findViewById(R.id.tNome)
        var img: ImageView = view.findViewById(R.id.img)
        var progress: ProgressBar = view.findViewById(R.id.progress)
        var cardView: CardView = view.findViewById(R.id.card_view)
    }

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
        val context = holder.itemView.context
        // Recupera o objeto carro
        val carro = carros[position]
        // Atualiza os dados do carro
        holder.tNome.text = carro.nome
        holder.progress.visibility = View.VISIBLE
        // Faz o download da foto e mostra o ProgressBar
        if (carro.urlFoto.trim().isEmpty()) {
            // Deixa o ImageView vazio se não tem foto
            holder.img.setImageBitmap(null)
        } else {
            // Faz o download da foto e mostra o ProgressBar
            Picasso.with(context).load(carro.urlFoto).fit().into(holder.img,
                object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        // Download OK
                        holder.progress.visibility = View.GONE
                    }

                    override fun onError() {
                        holder.progress.visibility = View.GONE
                    }
                })
        }
        holder.itemView.setOnClickListener { onClick(carro) }
    }

}