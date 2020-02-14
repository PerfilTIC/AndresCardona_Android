package com.cardona.musicdemo.model.persistence.TypeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ListTypeConverter {

    @TypeConverter
    fun stringObjectList(data: String?): List<String?> {
        if (data == null) {
            return listOf()
        }
        val listType: Type = object : TypeToken<List<String?>>() {}.type

        val gson = Gson()
        return gson.fromJson<List<String?>>(data, listType)
    }

    @TypeConverter
    fun objectListToString(someObjects: List<String?>): String? {
        val gson = Gson()
        return gson.toJson(someObjects)
    }

}