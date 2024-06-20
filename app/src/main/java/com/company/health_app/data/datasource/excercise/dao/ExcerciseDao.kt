package com.company.health_app.data.datasource.excercise.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity

@Dao
interface ExcerciseDao {
    @Insert
    fun insert(excerciseEntity : ExcerciseEntity)

    @Delete
    fun delete(excerciseEntity: ExcerciseEntity)

    @Update
    fun update(excerciseEntity: ExcerciseEntity)

    @Query("SELECT * from excerciseroutine ORDER BY name DESC LIMIT 1")
    fun getLatestWord() : ExcerciseEntity

    @Query("SELECT * from excerciseroutine ORDER BY name DESC")
    fun getAll() : List<ExcerciseEntity>

    @Query("DELETE from excerciseroutine")
    fun deleteAll()

    @Query("DELETE FROM excerciseroutine WHERE name = :nameToDelete")
    fun deleteByName(nameToDelete: String)

}