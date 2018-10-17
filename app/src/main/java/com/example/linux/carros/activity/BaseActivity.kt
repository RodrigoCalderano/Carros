package com.example.linux.carros.activity

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered") // Não reclamar que não está no manifest
open class BaseActivity : AppCompatActivity(){
    // Propriedade para acessar o contexto de qualquer lugar
    protected  val context: Context get() = this
    // Métodos comuns para todas activities vão aqui:
}