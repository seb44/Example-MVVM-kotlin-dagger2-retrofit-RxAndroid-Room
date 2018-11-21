package com.transports.mvvmtan.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface TanArretDao {

    @Query("SELECT * from tan_arret ORDER BY code_lieu ASC")
    fun getTanArrets(): List<TanArret>

    @Insert
    fun insertTanArret(tanArret: TanArret)

    @Query("DELETE FROM tan_arret")
    fun deleteAllTanArrets()

    @Update
    fun updateTanArret(tanArret: TanArret)

    @Insert
    fun insertTanArrets(vararg tanArret: TanArret)
}