package com.company.health_app.data.datasource.excercise.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity

@Database(entities = [ExcerciseEntity::class] , version = 1, exportSchema = false)
abstract class ExcerciseDatabase : RoomDatabase() {
    abstract fun excerciseDao() : ExcerciseDao

    companion object {
        private var INSTANCE : ExcerciseDatabase? = null
        fun getInstance(context: Context) : ExcerciseDatabase? {
            if (INSTANCE == null) {
                synchronized(ExcerciseDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ExcerciseDatabase::class.java,
                        "excercise_database.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}