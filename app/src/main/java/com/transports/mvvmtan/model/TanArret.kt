package com.transports.mvvmtan.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "tan_arret")
data class TanArret (

    @PrimaryKey
    @ColumnInfo(name="code_lieu")
    var codeLieu: String,

    @ColumnInfo(name="libelle")
    var libelle: String? = null,

    @ColumnInfo(name="distance")
    var distance: String? = null,

    @ColumnInfo(name="ligne")
    var ligne: List<Ligne>? = null,

    @ColumnInfo(name="latitude")
    var latitude: String? = null,

    @ColumnInfo(name="longitude")
    var longitude: String? = null
)
