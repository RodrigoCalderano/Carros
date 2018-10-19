package com.example.linux.carros.activity

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.linux.carros.R
import com.example.linux.carros.adapter.CarroAdapter
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.domain.CarroService
import com.example.linux.carros.domain.TipoCarro
import com.example.linux.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carros.*
import org.jetbrains.anko.toast

class CarrosActivity : BaseActivity() {

    private var tipo = TipoCarro.Classicos
    private var carros = listOf<Carro>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)
        // Inicializações necessárias (toolbar, up navigation, views..)
        createSetup()
    }

    // Inicializar lógica da tela
    override fun onResume() {
        super.onResume()
        taskCarros()
    }

    private fun createSetup() {
        setupToolbar(R.id.toolbarCarros)
        // Liga o up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Lê o tipo dos argumentos vieram por put extra
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        // RecylcerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    private fun taskCarros() {
        // Busca carros
        this.carros = CarroService.getCarros(context,tipo)
        // Atualiza a lsita
        // Quando se tem somente um parâmetro, ele se chama IT por padrão
        recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it) }
    }

    private fun onClickCarro(carro: Carro) {
        toast("Clicou no carro ${carro.nome}")
    }
}
