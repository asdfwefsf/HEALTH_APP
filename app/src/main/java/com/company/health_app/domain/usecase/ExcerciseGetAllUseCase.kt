package com.company.health_app.domain.usecase

import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseGetAllRepository
import javax.inject.Inject

class ExcerciseGetAllUseCase @Inject constructor(
    private val excerciseGetAllRepository : ExcerciseGetAllRepository
) {
    suspend operator fun invoke() : List<ExcerciseModel> {
        return excerciseGetAllRepository.excerciseGetAll()
    }
}