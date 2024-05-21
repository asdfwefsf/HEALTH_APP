package com.company.health_app.domain.usecase

import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseInsertRepository
import javax.inject.Inject

class ExcerciseInsertUseCase @Inject constructor(
    private val excerciseInsertRepository: ExcerciseInsertRepository
) {
    suspend operator fun invoke (excerciseModel: ExcerciseModel) {
        excerciseInsertRepository.InsertExcercise(excerciseModel)
    }
}