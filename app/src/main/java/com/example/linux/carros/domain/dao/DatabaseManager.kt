package com.example.linux.carros.domain.dao

import android.arch.persistence.room.Room
import com.example.linux.carros.CarrosApplication

object DatabaseManager {
    // Singleton do Room
    private var dbInstance: CarrosDatabase

    init {
        val appContext = CarrosApplication.getInstance().applicationContext
        // Configura o Room
        dbInstance = Room.databaseBuilder(appContext,
            CarrosDatabase::class.java, "carros.sqlite").build()
    }

    fun getCarroDAO(): CarroDAO {
        return dbInstance.carroDAO()
    }
}