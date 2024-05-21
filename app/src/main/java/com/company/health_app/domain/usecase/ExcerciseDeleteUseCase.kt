package com.company.health_app.domain.usecase

import com.company.health_app.domain.repository.ExcerciseRepository
import javax.inject.Inject

class ExcerciseDeleteUseCase @Inject constructor(
    private val repository : ExcerciseRepository
) {
    operator suspend fun invoke() {
        repository.deleteAll()
    }
}