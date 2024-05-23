package com.company.health_app.domain.usecase

import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseDeleteRepository
import javax.inject.Inject

class ExcerciseDeleteUseCase @Inject constructor(
    private val excerciseDeleteRepository : ExcerciseDeleteRepository
) {
    suspend operator fun invoke(excercise : ExcerciseModel) {
        excerciseDeleteRepository.ExcerciseDelete(excercise)
    }
}