package com.transports.mvvmtan.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.transports.mvvmtan.model.TanArret
import com.transports.mvvmtan.model.TanArretDao

@Database(entities = arrayOf(TanArret::class), version = 1)
@TypeConverters(ConvertNumLigne::class)
abstract class TanDatabase : RoomDatabase() {
    abstract fun arretTanDao(): TanArretDao
}