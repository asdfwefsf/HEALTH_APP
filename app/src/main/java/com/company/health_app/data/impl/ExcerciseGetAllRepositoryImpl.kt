package com.company.health_app.data.impl

import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseModel
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseGetAllRepository
import javax.inject.Inject

class ExcerciseGetAllRepositoryImpl @Inject constructor(
    private val dao : ExcerciseDao
) : ExcerciseGetAllRepository {
    override suspend fun excerciseGetAll() : List<ExcerciseModel> {
        return dao.getAll().map {
            it.toExcerciseModel()
        }
    }

}