package com.company.health_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.health_app.databinding.ActivityMainBinding
import com.company.health_app.difault.DefaultActivity


class MainActivity : ComponentActivity() , ExcerciseAdapter.ExcerciseItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var excerciseAdapter: ExcerciseAdapter
    private val deleteAllLiveData = MutableLiveData<Boolean>()

    // 함수로 사용될 변수
    private val newRoutine = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        val updated = result.data?.getBooleanExtra("updated" , false) ?: false
        if(result.resultCode == RESULT_OK && updated ){
            updateAddWord()
        }
    }

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }
        initRecyclerView()
        binding.callExcerciseAdd.setOnClickListener {
            Intent(this , ExcerciseAddActivity::class.java).let { // ExcerciseAddActivity로 이동.
                newRoutine.launch(it)
            }
        }


        binding.endButton.setOnClickListener {
            Thread{
                deleteAll()
                deleteAllLiveData.postValue(true)
            }.start()
        }

        deleteAllLiveData.observe(this) { isDeleted ->
            if (isDeleted) {
                initRecyclerView()
                Toast.makeText(this, "데이터 테이블 삭제", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToFragmentButton.setOnClickListener {
            val intent = Intent(this , DefaultActivity::class.java)
            startActivity(intent)
            fromDefaultFragmentToMainActivity()
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        // View(RecyclerView)와 Data 를 연결 하기 위해서 Adapter 를 만들 꺼야.
        excerciseAdapter = ExcerciseAdapter(mutableListOf(), this)
        binding.recyclerView.apply {
            adapter = excerciseAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            // RecyclerView 만들 때 : Adapter 연결 하기 && layoutManager 추가 하기
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
        Thread {
            val list = AppDatabase.getInstance(this)?.excerciseDao()?.getAll() ?: emptyList()
            // AppDatabase.getInstance(this) : MainActivity 와 AppDatabase 객체를 연결
            // DataBase 에서 내용을 부르는 거야
            excerciseAdapter.list.addAll(list)
            //Adapter 의 list 에 Data 를 넣어 준거야.
            runOnUiThread{
                excerciseAdapter.notifyDataSetChanged()

                // Data 넣었 다고 알려 주는 거야 , notifyDataSetChanged() 에 의해서 UI가 변경이 될 것이 므로 Thread 를 만들어 줘야 해.
            }
        }.start()
    }

    private fun updateAddWord() { // // roomDatabase에 최근에 추가된 Data 가져와서 Adapter에 새로운 Data로 Collection에 넣어줘
        Thread {
            AppDatabase.getInstance(this)?.excerciseDao()?.getLatestWord()?.let { excercise ->
                excerciseAdapter.list.add(0, excercise) //Update 할 때 Data 를 추가 하고
                runOnUiThread {
//                    excerciseAdapter.notifyDataSetChanged()
                    excerciseAdapter.notifyItemInserted(0)
                }
            }
        }.start()
    }

    private fun deleteAll() {
        AppDatabase.getInstance(this)?.excerciseDao()?.deleteAll()
    }


    private fun fromDefaultFragmentToMainActivity() {
        val intent = Intent().getBooleanExtra("endDefaultFragment" , false)
        if(intent) {
            initRecyclerView()
        }
    }




    override fun onItemDeleteClick(excercise: Excercise) {
        Thread{
            AppDatabase.getInstance(this)?.excerciseDao()?.delete(excercise)
            runOnUiThread {
                initRecyclerView()
            }
        }.start()
    }

}