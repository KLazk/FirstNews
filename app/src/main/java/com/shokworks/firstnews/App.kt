package com.shokworks.firstnews

import android.app.Application
import androidx.room.Room
import com.shokworks.firstnews.dbRoom.DataBase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        /** Instancia de la base de datos, no debe utilizarse directamente. Deben utilizarse los metodos de forma segura a travez de su implementación en Repository */
        lateinit var database: DataBase

        fun applicationContext() : App {
            return instance as App
        }

    }

    override fun onCreate() {
        super.onCreate()
        /**----Creacion de las tablas */
        database = Room.databaseBuilder(this, DataBase::class.java, "table_Noticies").fallbackToDestructiveMigration().build()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}