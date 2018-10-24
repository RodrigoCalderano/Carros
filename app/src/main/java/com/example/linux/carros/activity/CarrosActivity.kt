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
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CarrosActivity : BaseActivity() {

    private var tipo = TipoCarro.Classicos
    private var carros = listOf<Carro>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_carros)
        // Inicializações necessárias (toolbar, up navigation, views..)
        setupToolbar(R.id.toolbarCarros)
        // Liga o up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Lê o tipo dos argumentos vieram por put extra
        this.tipo = intent.getSerializableExtra("tipoP") as TipoCarro
        // Título
        val s = context.getString(tipo.string)
        supportActionBar?.title = s

        //Adiciona o fragment no layout
        if (savedInstanceState == null) {
            // Cria uma instância do fragment, e configura os argumentos
            val frag = CarrosFragment()
            // Dentre os argumentos que foram passados para a activity, está o tipo do carro
            frag.arguments = intent.extras
            // Adiciona o fragment no layout de marcação
            supportFragmentManager.beginTransaction().add(R.id.container, frag).commit()
        }
    }
}

