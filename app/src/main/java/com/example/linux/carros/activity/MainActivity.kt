package com.example.linux.carros.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.example.linux.carros.R
import com.example.linux.carros.adapter.TabsAdapter
import com.example.linux.carros.domain.TipoCarro
import com.example.linux.carros.extensions.setupToolbar
import com.example.linux.carros.utils.PermissionUtils
import com.example.linux.carros.utils.Prefs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Toolbar:
        setupToolbar(R.id.toolbarCarros)
        // Menu lateral:
        setUpNavDrawer()
        // Tabs
        setupViewPagerTabs()
        // FAB
        fab.setOnClickListener {
            // Abre a tela de cadastro
            startActivity<CarroFormActivity>()
        }
        // Solicita as permissões
        PermissionUtils.validate(this, 1,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, BOB! Do something!
                alertAndFinish()
                return
            }
        }
    }

    // Mostra o alerta de erro e fecha o aplicativo
    private fun alertAndFinish() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa " +
                "aceitar as permissões")
        builder.setPositiveButton("OK") { dialog, id -> finish() }
        builder.create().show()
    }

    private fun setUpNavDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbarCarros, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun setupViewPagerTabs(){
        // Configura o ViewPager + Tabs
        // As variáveis viewPager e tabLayout são geradas automaticamente pelo Kotlin Extensions
        // Mantem 2 a mais do que está exibindo

        viewPager.offscreenPageLimit = 3
        viewPager.adapter = TabsAdapter(context, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        // Cor branca no texto
        val cor = ContextCompat.getColor(context, R.color.white)
        tabLayout.setTabTextColors(cor, cor)
        // Cor da barrinha de seleção
        tabLayout.setSelectedTabIndicatorColor(cor)

        // Salva e Recupera a última tab acessada
        // Ao fechar o app, ele volta na tab que estava ao fechar
        val tabIdx = Prefs.tabIdx
        viewPager.currentItem = tabIdx
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) { }
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) { }
            override fun onPageSelected(page: Int) {
                // Salva o índice da página/tab selecionada
                Prefs.tabIdx = page
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_carros_todos -> {
                toast("Clicou em carros")
            }
            R.id.nav_item_carros_classicos -> {
                startActivity<CarrosActivity>("tipoP" to TipoCarro.Classicos)
            }
            R.id.nav_item_carros_esportivos -> {
                startActivity<CarrosActivity>("tipoP" to TipoCarro.Esportivos)
            }
            R.id.nav_item_carros_luxo -> {
                startActivity<CarrosActivity>("tipoP" to TipoCarro.Luxo)
            }
            R.id.nav_item_site_livro -> {
                startActivity<SiteLivroActivity>()
            }
            R.id.nav_item_settings -> {
                toast("Clicou em configurações")
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
