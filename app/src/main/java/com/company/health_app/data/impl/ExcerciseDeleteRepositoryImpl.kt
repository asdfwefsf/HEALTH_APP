package com.company.health_app.data.impl

import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseEntity
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseDeleteRepository
import javax.inject.Inject

class ExcerciseDeleteRepositoryImpl @Inject constructor(
    private val dao : ExcerciseDao
) : ExcerciseDeleteRepository {
    override suspend fun ExcerciseDelete(excercise: ExcerciseModel) {
        val mapped = excercise.toExcerciseEntity()
        dao.delete(mapped)
    }

}