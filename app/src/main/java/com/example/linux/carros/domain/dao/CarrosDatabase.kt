package com.example.linux.carros.domain.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.linux.carros.domain.Carro

@Database(entities = arrayOf(Carro::class), version = 1)
    abstract class CarrosDatabase: RoomDatabase(){
        abstract fun carroDAO(): CarroDAO
}

