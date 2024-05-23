package com.company.health_app.domain.usecase

import com.company.health_app.domain.repository.ExcerciseRepository
import javax.inject.Inject

class ExcerciseDeleteAllUseCase @Inject constructor(
    private val repository : ExcerciseRepository
) {
    operator suspend fun invoke() {
        repository.deleteAll()
    }
}