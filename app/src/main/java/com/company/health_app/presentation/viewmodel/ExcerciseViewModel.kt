package com.company.health_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.domain.usecase.ExcerciseDeleteAllUseCase
import com.company.health_app.domain.usecase.ExcerciseDeleteUseCase
import com.company.health_app.domain.usecase.ExcerciseGetAllUseCase
import com.company.health_app.domain.usecase.ExcerciseInsertUseCase
import com.company.health_app.domain.usecase.ExcerciseUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExcerciseViewModel @Inject constructor(
    private val excerciseDeleteAllUseCase: ExcerciseDeleteAllUseCase,
    private val excerciseGetAllUseCase: ExcerciseGetAllUseCase,
    private val excerciseInsertUseCase: ExcerciseInsertUseCase,
    private val excerciseUpdateUseCase: ExcerciseUpdateUseCase,
    private val edxcerciseDeleteUseCase: ExcerciseDeleteUseCase
) : ViewModel() {
    init {
        getAllExcercise()
    }
    // 운동 루틴 추가
    fun insert(excercise: ExcerciseModel) = viewModelScope.launch(Dispatchers.IO) {
        excerciseInsertUseCase(excercise)
    }
    // 운동 루틴 제거
    fun deleteExcercise(excercise: ExcerciseModel) = viewModelScope.launch(Dispatchers.IO) {
        edxcerciseDeleteUseCase(excercise)
        getAllExcercise() // 데이터 삭제 후 최신 데이터를 가져와서 업데이트
    }
    // 운동 루틴 전체 삭제
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        excerciseDeleteAllUseCase()
    }
    // 모든 운동 루틴 가져와
    private val _allExcercises = MutableLiveData<List<ExcerciseModel>>()
    val allExcercises: LiveData<List<ExcerciseModel>> = _allExcercises
    fun getAllExcercise() = viewModelScope.launch(Dispatchers.IO) {
        val excercises = excerciseGetAllUseCase()
        // postValue 호출되면 allExcercises를 구독하는 모든 활성상태 관찰자들에게 새로운 데이터 전달됨.
        _allExcercises.postValue(excercises)
    }
    // 운동 루틴 정보 업데이트
    fun updateExcercise(excercise: ExcerciseModel) = viewModelScope.launch(Dispatchers.IO) {
        excerciseUpdateUseCase(excercise)
    }

}