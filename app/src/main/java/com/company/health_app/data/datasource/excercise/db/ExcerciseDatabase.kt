package com.company.health_app.data.datasource.excercise.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity

@Database(
    entities = [ExcerciseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExcerciseDatabase : RoomDatabase() {
    abstract fun excerciseDao() : ExcerciseDao
}