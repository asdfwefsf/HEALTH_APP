package com.company.health_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.usecase.ExcerciseDeleteAllUseCase
import com.company.health_app.domain.usecase.ExcerciseDeleteUseCase
import com.company.health_app.domain.usecase.ExcerciseGetAllUseCase
import com.company.health_app.domain.usecase.ExcerciseGetLatestWordUseCase
import com.company.health_app.domain.usecase.ExcerciseInsertUseCase
import com.company.health_app.domain.usecase.ExcerciseUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExcerciseViewModel @Inject constructor(
//    application: Application,
    private val deleteUseCase: ExcerciseDeleteAllUseCase,
    private val getLatestWordUseCase: ExcerciseGetLatestWordUseCase,
    private val excerciseGetAllUseCase: ExcerciseGetAllUseCase,
    private val excerciseInsertUseCase: ExcerciseInsertUseCase,
    private val excerciseUpdateUseCase : ExcerciseUpdateUseCase,
    private val edxcerciseDeleteUseCase : ExcerciseDeleteUseCase


    ) : ViewModel() {

//    private val repository: ExcerciseRepository
//    val allExcercises: LiveData<List<Excercise>>

    init {
        GetAllExcercise()
    }

    // custom 운동 루틴 변수 및 함수
    private val _latestWord = MutableLiveData<ExcerciseModel>()
    val latestWord: LiveData<ExcerciseModel> get() = _latestWord
    fun updateAddWord() = viewModelScope.launch(Dispatchers.IO) {
        val latestWord = getLatestWordUseCase()
        withContext(Dispatchers.Main) {
            _latestWord.value = latestWord
        }
    }


    fun insert(excercise: ExcerciseModel) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(excercise)
        excerciseInsertUseCase(excercise)
    }


    fun DeleteAll() = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(excercise)
        deleteUseCase()
    }

    fun DeleteExcercise(excercise: ExcerciseModel) = viewModelScope.launch(Dispatchers.IO) {
        edxcerciseDeleteUseCase(excercise)
        GetAllExcercise() // 데이터 삭제 후 최신 데이터를 가져와서 업데이트

    }



    // 모든 운동 루틴
    private val _allExcercises = MutableLiveData<List<ExcerciseModel>>()
    val allExcercises: LiveData<List<ExcerciseModel>> = _allExcercises
    fun GetAllExcercise() = viewModelScope.launch (Dispatchers.IO) {
        val excercises = excerciseGetAllUseCase()
        _allExcercises.postValue(excercises)
    }


//
    fun UpdateExcercise(excercise: ExcerciseModel) = viewModelScope.launch(Dispatchers.IO) {
//        repository.update(excercise)
        excerciseUpdateUseCase(excercise)
    }
//
//    fun delete(excercise: Excercise) = viewModelScope.launch(Dispatchers.IO) {
//        repository.delete(excercise)
//    }
//
//    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
//        repository.deleteAll()
//    }

//    fun getLatestWord(): LiveData<Excercise?> = liveData(Dispatchers.IO) {
//        emit(repository.getLatestWord())
//    }
}