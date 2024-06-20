package com.company.health_app.di

import android.content.Context
import androidx.room.Room
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.db.ExcerciseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // 룸DB 객체 반환(싱글턴)
    @Provides
    @Singleton
    fun provideDatabse(context : Context) : ExcerciseDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ExcerciseDatabase::class.java,
            "excercise_database.db"
        ).build()
    }

    // 이제는 다오는 공짜
    @Provides
    @Singleton
    fun provideExcerciseDao(database: ExcerciseDatabase) : ExcerciseDao {
        return database.excerciseDao()
    }

}