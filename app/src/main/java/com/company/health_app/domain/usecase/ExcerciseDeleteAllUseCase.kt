package com.company.health_app.domain.usecase

import com.company.health_app.domain.repository.ExcerciseDeleteAllRepository
import javax.inject.Inject

class ExcerciseDeleteAllUseCase @Inject constructor(
    private val repository : ExcerciseDeleteAllRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}