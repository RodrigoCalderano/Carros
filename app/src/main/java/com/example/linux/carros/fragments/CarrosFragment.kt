package com.example.linux.carros.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linux.carros.R
import com.example.linux.carros.adapter.CarroAdapter
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.domain.CarroService
import com.example.linux.carros.domain.TipoCarro
import com.example.linux.carros.extensions.setupToolbar
import com.example.linux.carros.fragments.BaseFragment
import kotlinx.android.synthetic.main.activity_carros.*
import kotlinx.android.synthetic.main.fragment_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Suppress("DEPRECATION")
class CarrosFragment : BaseFragment() {

    private var tipo = TipoCarro.Classicos
    private var carros = listOf<Carro>()

    // Cria a view do fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_carros, container, false)
        // Lê o tipo dos argumentos
        this.tipo = arguments?.getSerializable("tipoP") as TipoCarro
        return view
    }

    // Inicializa as views
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    // Inicializar lógica da tela
    override fun onResume() {
        super.onResume()
        taskCarros()
    }

    private fun taskCarros() {
        // Liga a animação do ProgressBar
        progress.visibility = View.VISIBLE
        object : Thread() {
            override fun run() {
                // Busca carros
                carros = CarroService.getCarros(tipo)
                // Atualiza a lista
                activity?.runOnUiThread(Runnable {
                    recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it) }
                    // Esconde o ProgressBar
                    progress.visibility = View.INVISIBLE
                })
            }
        }.start()
    }

    // Trata o evento de clique no carro
    fun onClickCarro(carro: Carro){
        activity?.startActivity<CarroActivity>("carroParamCarrosFrag2CarroAct" to carro)
    }
}

