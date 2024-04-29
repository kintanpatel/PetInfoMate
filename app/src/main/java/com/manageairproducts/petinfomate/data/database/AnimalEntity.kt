package com.manageairproducts.petinfomate.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.manageairproducts.petinfomate.model.Animal


@Entity
data class AnimalEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "data") var data: String = ""
) {
    fun toAnimal(): Animal {
        val gson = Gson()
        return gson.fromJson(data, Animal::class.java)
    }
}

