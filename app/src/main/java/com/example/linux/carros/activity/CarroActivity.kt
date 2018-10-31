package com.example.linux.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.linux.carros.R
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.domain.CarroService
import com.example.linux.carros.domain.CarroServiceRetrofit
import com.example.linux.carros.domain.RefreshListEvent
import com.example.linux.carros.extensions.loadUrl
import com.example.linux.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_editar -> {
                // Abre a tela de cadastro e passa o carro como parâmetro
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }
            R.id.action_deletar -> {
                // Mostra o alerta de confirmação
                alert("Deseja excluir este carro?") {
                    title = "Alert"
                    positiveButton(R.string.sim) { taskDeletar() }
                    negativeButton(R.string.nao) { }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Deleta o carro
    private fun taskDeletar() {
        Observable.fromCallable { CarroServiceRetrofit.delete(carro) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                /** onNext **/
                // Dispara evento para atualizar a lista de carro
                EventBus.getDefault().post(RefreshListEvent())
                toast(it.msg)
                finish()
            },{
                /** onError **/
                toast("Ocorreu um erro ao deletar o carro")
            })
    }

}
