package com.company.health_app.domain.usecase

import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseUpdateRepository
import javax.inject.Inject

class ExcerciseUpdateUseCase @Inject constructor(
    private val excerciseUpdateRepository : ExcerciseUpdateRepository
) {
    suspend operator fun invoke(excercise : ExcerciseModel) {
        excerciseUpdateRepository.excerciseUpdate(excercise)
    }
}