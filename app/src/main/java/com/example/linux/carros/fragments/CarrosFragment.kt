package com.example.linux.carros.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.linux.carros.R
import com.example.linux.carros.activity.CarroActivity
import com.example.linux.carros.adapter.CarroAdapter
import com.example.linux.carros.domain.*
import com.example.linux.carros.extensions.setupToolbar
import com.example.linux.carros.fragments.BaseFragment
import com.example.linux.carros.utils.AndroidUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_carros.*
import kotlinx.android.synthetic.main.fragment_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Registra no bus de ventos
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancela o registro no bus de eventos
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: RefreshListEvent) {
        // Recebeu o evento
        taskCarros()
    }

    // Inicializa as views
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskCarros()
    }

    @SuppressLint("CheckResult")
    private fun taskCarros() {
        // Liga a animação do ProgressBar
        progress.visibility = View.VISIBLE
        CarroServiceRetrofit.getCarros(tipo) // busca os carros
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                /** onNext **/
                // Atualiza interface
                // it é o parâmetro default das lambdas, sendo que neste caso é a lista de carros
                recyclerView.adapter = CarroAdapter(it) { onClickCarro(it) }
                progress.visibility = View.INVISIBLE
            },{
                /** onError **/
                activity!!.toast("Ocorreu um erro!")
                progress.visibility = View.INVISIBLE
            })
    }


    // Trata o evento de clique no carro
    fun onClickCarro(carro: Carro){
        activity?.startActivity<CarroActivity>("carroParamCarrosFrag2CarroAct" to carro)
    }
}

