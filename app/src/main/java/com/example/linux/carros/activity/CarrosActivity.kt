package com.example.linux.carros.activity

import android.os.Bundle
import com.example.linux.carros.R
import com.example.linux.carros.domain.TipoCarro
import com.example.linux.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carros.*

class CarrosActivity : BaseActivity() {

    private var tipo: TipoCarro = TipoCarro.Classicos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)
        setupToolbar(R.id.toolbarCarros)
        // Liga o up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Lê o tipo dos argumentos
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        val tipoCarroString = context.getString(tipo.string)
        // Mostra o tipo do carro no título da toolbar
        supportActionBar?.title = tipoCarroString
        // Mostra o tipo do carro na tela
        text.text = "Carros $tipoCarroString"
    }
}
