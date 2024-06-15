package com.company.health_app.data.datasource.excercise.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.company.health_app.data.datasource.excercise.entity.Book
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity
import com.company.health_app.data.datasource.excercise.entity.User

@Dao
interface ExcerciseDao {
    @Insert
    fun insert(excerciseEntity : ExcerciseEntity)

    @Delete
    fun delete(excerciseEntity: ExcerciseEntity)

    @Update
    fun update(excerciseEntity: ExcerciseEntity)

    @Query("SELECT * from ExcerciseEntity ORDER BY name DESC LIMIT 1")
    fun getLatestWord() : ExcerciseEntity

    @Query("SELECT * from ExcerciseEntity ORDER BY name DESC")
    fun getAll() : List<ExcerciseEntity>

    @Query("DELETE from ExcerciseEntity")
    fun deleteAll()

    @Query("DELETE FROM ExcerciseEntity WHERE name = :nameToDelete")
    fun deleteByName(nameToDelete: String)




    @Query(
        "SELECT * FROM user " +
                "JOIN book ON user.id = book.user_id"
    )
    fun loadUserAndBookNames(): Map<User, List<Book>>
}