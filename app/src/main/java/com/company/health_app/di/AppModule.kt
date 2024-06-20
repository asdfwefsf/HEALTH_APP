package com.company.health_app.di


import android.app.Application
import android.content.Context
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

//    @Provides
//    fun provideExcerciseRepository(impl : ExcerciseRepositoryImpl) : ExcerciseRepository = impl
//
//    @Provides
//    fun provideExcerciseGetAllRepository(impl : ExcerciseGetAllRepositoryImpl) : ExcerciseGetAllRepository = impl
//
//    @Provides
//    fun provideExcerciseGetLatestWordRepository(impl : ExcerciseGetLatestWordRepositoryImpl) : ExcerciseGetLatestWordRepository = impl
//
//    @Provides
//    fun provideExcerciseInsertRepository(impl : ExcerciseInsertRepositoryImpl) : ExcerciseInsertRepository = impl
//
//    @Provides
//    fun provideExcerciseUpdateRepository(impl : ExcerciseUpdateRepositoryImpl) : ExcerciseUpdateRepository = impl
//
//
//    @Provides
//    fun provideExcerciseDeleteRepository(impl : ExcerciseDeleteRepositoryImpl) : ExcerciseDeleteRepository = impl


}

