package com.company.health_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExcerciseDao {
    @Insert
    fun insert(excercise : Excercise)

    @Delete
    fun delete(excercise: Excercise)

    @Update
    fun update(excercise: Excercise)

    @Query("SELECT * from excercise ORDER BY id DESC LIMIT 1")
    fun getLatestWord() : Excercise

    @Query("SELECT * from excercise ORDER BY id DESC")
    fun getAll() : List<Excercise>

    @Query("DELETE from excercise")
    fun deleteAll()

    @Query("DELETE FROM Excercise WHERE name = :nameToDelete")
    fun deleteByName(nameToDelete: String)
}