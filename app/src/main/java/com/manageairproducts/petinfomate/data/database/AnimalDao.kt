package com.manageairproducts.petinfomate.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AnimalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: AnimalEntity): Long

    @Query("SELECT * FROM animalentity")
    fun getAll(): List<AnimalEntity>

    @Delete
    fun delete(kidsItem: AnimalEntity)

}