package com.company.health_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseEntity
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseModel
import com.company.health_app.databinding.ActivityExcerciseAddBinding
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.presentation.viewmodel.ExcerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExcerciseAddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExcerciseAddBinding
    private lateinit var excerciseEntity : ExcerciseEntity
    private val excerciseViewModel: ExcerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.inputName.text.toString()
            val setNum = binding.inputSetNum.text.toString()
            if (name.isNotEmpty() && setNum.isNotEmpty()) {
                excerciseEntity = ExcerciseModel(0 , name , setNum.toInt()).toExcerciseEntity()
                excerciseViewModel.insert(excerciseEntity.toExcerciseModel())
                observeInsertResult()
            } else {
                Toast.makeText(this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }
    }

    private fun observeInsertResult() {
            Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent().putExtra("updated", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }




