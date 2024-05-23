package com.company.health_app


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.db.ExcerciseDatabase
import com.company.health_app.data.impl.ExcerciseDeleteRepositoryImpl
import com.company.health_app.data.impl.ExcerciseGetAllRepositoryImpl
import com.company.health_app.data.impl.ExcerciseGetLatestWordRepositoryImpl
import com.company.health_app.data.impl.ExcerciseInsertRepositoryImpl
import com.company.health_app.data.impl.ExcerciseRepositoryImpl
import com.company.health_app.data.impl.ExcerciseUpdateRepositoryImpl
import com.company.health_app.domain.repository.ExcerciseDeleteRepository
import com.company.health_app.domain.repository.ExcerciseGetAllRepository
import com.company.health_app.domain.repository.ExcerciseGetLatestWordRepository
import com.company.health_app.domain.repository.ExcerciseInsertRepository
import com.company.health_app.domain.repository.ExcerciseRepository
import com.company.health_app.domain.repository.ExcerciseUpdateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ExcerciseDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ExcerciseDatabase::class.java,
            "excercise_database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideExcerciseDao(database: ExcerciseDatabase): ExcerciseDao {
        return database.excerciseDao()
    }

    @Provides
    fun provideExcerciseRepository(impl : ExcerciseRepositoryImpl) : ExcerciseRepository = impl

    @Provides
    fun provideExcerciseGetAllRepository(impl : ExcerciseGetAllRepositoryImpl) : ExcerciseGetAllRepository = impl

    @Provides
    fun provideExcerciseGetLatestWordRepository(impl : ExcerciseGetLatestWordRepositoryImpl) : ExcerciseGetLatestWordRepository = impl

    @Provides
    fun provideExcerciseInsertRepository(impl : ExcerciseInsertRepositoryImpl) : ExcerciseInsertRepository = impl

    @Provides
    fun provideExcerciseUpdateRepository(impl : ExcerciseUpdateRepositoryImpl) : ExcerciseUpdateRepository = impl

    @Provides
    fun provideExcerciseDeleteRepository(impl : ExcerciseDeleteRepositoryImpl) : ExcerciseDeleteRepository = impl
}

