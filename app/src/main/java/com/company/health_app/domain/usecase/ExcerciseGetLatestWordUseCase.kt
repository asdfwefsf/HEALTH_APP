package com.company.health_app.domain.usecase

import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.repository.ExcerciseGetLatestWordRepository
import javax.inject.Inject

class ExcerciseGetLatestWordUseCase @Inject constructor(
    private val ExcerciseGetLatestWordRepository : ExcerciseGetLatestWordRepository
) {
    suspend operator fun invoke() : ExcerciseModel {
        return ExcerciseGetLatestWordRepository.GetExcerciseGetLatestWord()
    }
}