package com.example.linux.carros.activity

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
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CarrosFragment : BaseFragment() {

    private var tipo = TipoCarro.Classicos
    private var carros = listOf<Carro>()

    // Cria a view do fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_carros, container, false)
        // Lê o tipo dos argumentos
        this.tipo = arguments?.getSerializable("tipo") as TipoCarro
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
        // Busca carros
        this.carros = CarroService.getCarros(context,tipo)
        // Atualiza a lsita
        // Quando se tem somente um parâmetro, ele se chama IT por padrão
        recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it) }
    }

    private fun onClickCarro(carro: Carro) {
        // passando o objeto carro por parâmetro na intent (Carro é serializable)
        // o fragment não possui o método start activity, poir isso usar a host activity
        activity?.startActivity<CarroActivity>("carroParam" to carro)
    }
}
