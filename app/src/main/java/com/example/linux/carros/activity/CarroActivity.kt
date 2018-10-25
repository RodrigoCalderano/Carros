package com.example.linux.carros.activity

import android.os.Bundle
import com.example.linux.carros.R
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.extensions.loadUrl
import com.example.linux.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*

class CarroActivity : BaseActivity() {
    // Pega o carro que é passado por parâmetro através de serializable
    // by lazy significa que só vai instanciar quando precisar usar
    val carro : Carro by lazy { intent.getParcelableExtra<Carro>("carroParamCarrosFrag2CarroAct") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)
        createSetup()
    }

    private fun createSetup() {
        setupToolbar(R.id.toolbarAC, carro.nome, true)
        // Atualiza a descrição do carro
        tDesc.text = carro.desc
        // Mostra a foto do carro (feito na extensão Picasso.kt)
        appBarImg.loadUrl(carro.urlFoto)
    }
}
