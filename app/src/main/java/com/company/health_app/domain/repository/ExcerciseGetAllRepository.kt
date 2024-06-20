package com.company.health_app.domain.repository

import com.company.health_app.domain.model.ExcerciseModel

interface ExcerciseGetAllRepository {
    suspend fun excerciseGetAll() : List<ExcerciseModel>
}