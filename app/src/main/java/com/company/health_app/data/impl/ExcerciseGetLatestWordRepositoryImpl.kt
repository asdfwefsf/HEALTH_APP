package com.company.health_app.data.impl

import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseModel
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseGetLatestWordRepository
import javax.inject.Inject

class ExcerciseGetLatestWordRepositoryImpl @Inject constructor(
    private val dao : ExcerciseDao
) : ExcerciseGetLatestWordRepository {
    override suspend fun getExcerciseGetLatestWord(): ExcerciseModel {
         return dao.getLatestWord().toExcerciseModel()
    }

}