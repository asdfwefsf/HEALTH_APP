package com.company.health_app.presentation.viewmodelfactory

//class ExcerciseViewModelFactory(
//    private val deleteUseCase: ExcerciseDeleteUseCase
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ExcerciseViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ExcerciseViewModel(deleteUseCase) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}