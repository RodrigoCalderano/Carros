package com.example.linux.carros.activity

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.example.linux.carros.R
import com.example.linux.carros.domain.*
import com.example.linux.carros.domain.FavoritosService.isFavorito
import com.example.linux.carros.extensions.loadUrl
import com.example.linux.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CarroActivity : BaseActivity() {
    // Pega o carro que é passado por parâmetro através de serializable
    // by lazy significa que só vai instanciar quando precisar usar
    val carro : Carro by lazy { intent.getParcelableExtra<Carro>("carroParamCarrosFrag2CarroAct") }
    protected var favorito = false

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
        // fab
        setFabColor()
        fab.setOnClickListener { onClickFavoritar(carro) }
    }

    private fun onClickFavoritar(carro: Carro) {
        taskFavoritar(carro)
    }

    @SuppressLint("CheckResult")
    private fun taskFavoritar(carro: Carro) {
        Observable.fromCallable {
            // Salva o carro no banco de dados
            FavoritosService.favoritar(carro)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favoritado ->
                // Atualiza a interface
                // Dispara evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                // Alerta de sucesso
                toast(if (favoritado) R.string.msg_carro_favoritado
                    else R.string.msg_carro_desfavoritado)
            }
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
        @Suppress
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

    private fun setFabColor() {
        @Suppress
        Observable.fromCallable {
            favorito = isFavorito(carro)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val fundo = ContextCompat.getColor(this, if (!favorito) R.color.favorito_on
                else R.color.favorito_off)
                val cor = ContextCompat.getColor(this, if (!favorito) R.color.yellow
                else R.color.favorito_on)
                fab.backgroundTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(fundo))
                fab.setColorFilter(cor)
                if(favorito) fab.setImageResource(R.drawable.ic_action_remove)
            }
    }

}
