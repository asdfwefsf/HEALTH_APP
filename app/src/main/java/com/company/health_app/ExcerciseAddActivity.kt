package com.company.health_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.health_app.databinding.ActivityExcerciseAddBinding

class ExcerciseAddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExcerciseAddBinding
    private lateinit var excercise : Excercise

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.inputName.text.toString()
            val setNum = binding.inputSetNum.text.toString()

            if (name.isNotEmpty() && setNum.isNotEmpty()) {
                excercise = Excercise(name , setNum.toInt())
                updateRoutine()
            } else {
                Toast.makeText(this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show()
            }


        }

        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }
    }

    private fun updateRoutine() { // roomDatabase에 Data Update
//        val name = binding.inputName.text.toString()
//        val setNum = binding.inputSetNum.text.toString().toInt()
//        val excercise = Excercise(name , setNum)

        Thread {
            AppDatabase.getInstance(this)?.excerciseDao()?.insert(excercise)
            runOnUiThread {
                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent().putExtra("updated",true)
            setResult(RESULT_OK,intent)
            finish()
        }.start()
    }




}