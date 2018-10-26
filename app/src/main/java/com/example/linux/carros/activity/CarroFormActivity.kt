package com.example.linux.carros.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.linux.carros.R
import com.example.linux.carros.domain.Carro
import com.example.linux.carros.domain.CarroService
import com.example.linux.carros.domain.RefreshListEvent
import com.example.linux.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CarroFormActivity : AppCompatActivity() {

    val carro: Carro? by lazy { intent.getParcelableExtra<Carro>("carro") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)
        // Configura a Toolbar
        val title = carro?.nome ?: getString(R.string.novo_carro)
        setupToolbar(R.id.toolbarCarroForm, title, true)
        // Liga o up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Menu com o botão Salvar na toolbar
        menuInflater.inflate(R.menu.menu_carro_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_salvar -> {
                taskSalvar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Cria a thread para salvar o carro
    private fun taskSalvar() {
        doAsync {
            val c = getCarroForm()
            // Salva o carro
            val response = CarroService.save(c)
            uiThread {
                // Dispara evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                toast(response.msg)
                finish()
            }
        }
    }

    // Cria um carro com os valores do formulário
    fun getCarroForm(): Carro {
        val c = carro ?: Carro()
        c.tipo = getTipo()
        c.nome = tNome.getText().toString()
        c.desc = tDesc.getText().toString()
        return c
    }

    // Convert o valor do Radio para strign
    fun getTipo(): String {
        when(radioTipo.getCheckedRadioButtonId()) {
            R.id.tipoEsportivo -> return getString(R.string.esportivos)
            R.id.tipoLuxo -> return getString(R.string.luxo)
        }
        return getString(R.string.classico)
    }

}
