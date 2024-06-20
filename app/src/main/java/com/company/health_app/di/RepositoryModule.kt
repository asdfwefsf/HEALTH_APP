package com.company.health_app.di

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

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
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