package com.company.health_app.data.impl

import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseEntity
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseUpdateRepository
import javax.inject.Inject

class ExcerciseUpdateRepositoryImpl @Inject constructor(
    private val dao : ExcerciseDao
) : ExcerciseUpdateRepository {
    override suspend fun ExcerciseUpdate(excercise: ExcerciseModel) {
        val mapped = excercise.toExcerciseEntity()
        dao.update(mapped)
    }
}