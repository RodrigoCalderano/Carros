package com.example.linux.carros.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.linux.carros.fragments.CarrosFragment
import com.example.linux.carros.domain.TipoCarro
import com.example.linux.carros.fragments.FavoritosFragment

class TabsAdapter(private val context: Context, fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
    // Quantidade de tabs
    override fun getCount(): Int = 4

    // Retorna a posição
    private fun getTipoCarro(position: Int) = when (position) {
        0 -> TipoCarro.Classicos
        1 -> TipoCarro.Esportivos
        2 -> TipoCarro.Luxo
        else -> TipoCarro.Favoritos
    }

    // Título da Tab
    override fun getPageTitle(position: Int): CharSequence {
        val tipo = getTipoCarro(position)
        return context.getString(tipo.string)
    }

    // Fragment que vai mostrar a lista de carros
    override fun getItem(position: Int) : Fragment {
        if (position == 3) {
            // Favoritos
            return FavoritosFragment()
        }
        // Clássicos, Esportivos e Luxo
        val tipo = getTipoCarro(position)
        val f : Fragment = CarrosFragment()
        val arguments = Bundle()
        arguments.putSerializable("tipoP", tipo)
        f.arguments = arguments
        return f
    }
}