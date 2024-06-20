package com.company.health_app.data.impl

import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.domain.repository.ExcerciseDeleteAllRepository
import javax.inject.Inject

class ExcerciseDeleteAllRepositoryImpl @Inject constructor(
    private val dao: ExcerciseDao
) : ExcerciseDeleteAllRepository {
    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}