package com.company.health_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseModel
import com.company.health_app.databinding.ActivityExcerciseAddBinding
import com.company.health_app.presentation.viewmodel.ExcerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExcerciseAddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExcerciseAddBinding
    private lateinit var excerciseEntity : ExcerciseEntity

    // 리팩토링
    private val excerciseViewModel: ExcerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 리팩토링
//        excerciseViewModel = ViewModelProvider(this).get(ExcerciseViewModel::class.java)


        binding.saveButton.setOnClickListener {
            val name = binding.inputName.text.toString()
            val setNum = binding.inputSetNum.text.toString()

            if (name.isNotEmpty() && setNum.isNotEmpty()) {
                excerciseEntity = ExcerciseEntity(name , setNum.toInt())

                // 리팩토링
                excerciseViewModel.insert(excerciseEntity.toExcerciseModel())
                observeInsertResult()



//                updateRoutine()

            } else {
                Toast.makeText(this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show()
            }




        }

        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }
    }

    // 리팩토링
    private fun observeInsertResult() {
        // 관찰하여 저장 완료 후 액티비티 종료
//        excerciseViewModel.allExcercises.observe(this) {
            Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent().putExtra("updated", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }


//    private fun updateRoutine() { // roomDatabase에 Data Update
////        val name = binding.inputName.text.toString()
////        val setNum = binding.inputSetNum.text.toString().toInt()
////        val excercise = Excercise(name , setNum)
//
//        Thread {
//            ExcerciseDatabase.getInstance(this)?.excerciseDao()?.insert(excercise)
//            runOnUiThread {
//                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
//            }
//            val intent = Intent().putExtra("updated",true)
//            setResult(RESULT_OK,intent)
//            finish()
//        }.start()
//    }




