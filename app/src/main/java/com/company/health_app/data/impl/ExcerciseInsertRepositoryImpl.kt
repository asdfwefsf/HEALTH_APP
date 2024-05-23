package com.company.health_app.data.impl

import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseEntity
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseInsertRepository
import javax.inject.Inject

class ExcerciseInsertRepositoryImpl @Inject constructor(
    private val excerciseDao: ExcerciseDao
) : ExcerciseInsertRepository {
    override suspend fun InsertExcercise(excerciseModel: ExcerciseModel)  {
        return excerciseDao.insert(excerciseModel.toExcerciseEntity())
    }

}