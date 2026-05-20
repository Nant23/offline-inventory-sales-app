package com.ananta.pasal.source.local

import androidx.room.TypeConverter
import com.ananta.pasal.source.local.model.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderItems(items: List<OrderItem>): String =
        gson.toJson(items)

    @TypeConverter
    fun toOrderItems(json: String): List<OrderItem> =
        gson.fromJson(json, object : TypeToken<List<OrderItem>>() {}.type)
}