package com.transports.mvvmtan.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import com.transports.mvvmtan.model.database.TanDatabase
import com.transports.mvvmtan.ui.TanArret.TanArretListViewModel


class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TanArretListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, TanDatabase::class.java, "tan_database").build()
            @Suppress("UNCHECKED_CAST")
            return TanArretListViewModel(db.arretTanDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}