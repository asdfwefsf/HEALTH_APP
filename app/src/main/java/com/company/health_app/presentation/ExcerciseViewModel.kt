package com.company.health_app.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.company.health_app.AppDatabase
import com.company.health_app.Excercise
import com.company.health_app.domain.ExcerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExcerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExcerciseRepository
    val allExcercises: LiveData<List<Excercise>>

    init {
        val excerciseDao = AppDatabase.getInstance(application)?.excerciseDao()
        repository = ExcerciseRepository(excerciseDao!!)
        allExcercises = repository.allExcercises
    }

    fun insert(excercise: Excercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(excercise)
    }

    fun update(excercise: Excercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(excercise)
    }

    fun delete(excercise: Excercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(excercise)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun getLatestWord(): LiveData<Excercise?> = liveData(Dispatchers.IO) {
        emit(repository.getLatestWord())
    }
}