package com.example.linux.carros.fragments

import android.annotation.SuppressLint
import android.util.Log
import com.example.linux.carros.activity.CarroActivity
import com.example.linux.carros.adapter.CarroAdapter
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.domain.FavoritosService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_carros.*
import org.jetbrains.anko.startActivity

class FavoritosFragment : CarrosFragment() {

    protected var carrosFav = listOf<Carro>()

    // Usando Rx
    @SuppressLint("CheckResult")
    override fun taskCarros() {
        Observable.fromCallable {
            // Pega carro no banco de dados
            carrosFav = FavoritosService.getCarros()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // Atualiza interface
                recyclerView.adapter = CarroAdapter(carrosFav) { onClickCarro(it) }
            }
    }

    override fun onClickCarro(carro: Carro) {
        // Ao clicar no carro vamos navegar para a tela de detalhes
        activity?.startActivity<CarroActivity>("carroParamCarrosFrag2CarroAct" to carro)
    }
}