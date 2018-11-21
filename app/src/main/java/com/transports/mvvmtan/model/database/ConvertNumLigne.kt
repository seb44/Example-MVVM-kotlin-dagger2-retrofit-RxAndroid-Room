package com.transports.mvvmtan.model.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.transports.mvvmtan.model.Ligne

import java.util.*


class ConvertNumLigne {

    internal var gson = Gson()

        @TypeConverter
        fun stringToSomeObjectList(data: String?): List<Ligne> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<Ligne>>() {

            }.type

            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObjects: List<Ligne>): String {
            return gson.toJson(someObjects)
        }

}