package com.company.health_app.domain.repository

import com.company.health_app.domain.model.ExcerciseModel

interface ExcerciseInsertRepository {
    suspend fun insertExcercise(excerciseModel : ExcerciseModel)
}