package com.company.health_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.health_app.data.datasource.excercise.db.ExcerciseDatabase
import com.company.health_app.data.datasource.excercise.entity.mapper.toExcerciseModel
import com.company.health_app.databinding.ActivityMainBinding
import com.company.health_app.presentation.difault.DefaultActivity
import com.company.health_app.presentation.viewmodel.ExcerciseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var excerciseAdapter: ExcerciseAdapter
    private val deleteAllLiveData = MutableLiveData<Boolean>()

    // 리팩토링
    private val excerciseViewModel: ExcerciseViewModel by viewModels()

    // 리팩토링 1
    // 함수로 사용될 변수
    private val newRoutine = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        val updated = result.data?.getBooleanExtra("updated", false) ?: false
        if (result.resultCode == RESULT_OK && updated) {
            updateAddWord()
        }
    }

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 리팩토링

//        val factory = ExcerciseViewModelFactory()
//        excerciseViewModel = ViewModelProvider(this, factory).get(ExcerciseViewModel::class.java)

        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }

        initRecyclerView()

        binding.callExcerciseAdd.setOnClickListener {
            Intent(this, ExcerciseAddActivity::class.java).let { // ExcerciseAddActivity로 이동.
                newRoutine.launch(it)
            }
        }


        binding.endButton.setOnClickListener {
//            Thread{
//                deleteAll()
//                deleteAllLiveData.postValue(true)
//            }.start()

            // 리팩토링
            excerciseViewModel.DeleteAll()
            deleteAllLiveData.postValue(true)

        }

        deleteAllLiveData.observe(this) { isDeleted ->
            if (isDeleted) {
                initRecyclerView()
                Toast.makeText(this, "데이터 테이블 삭제", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToFragmentButton.setOnClickListener {
            val intent = Intent(this, DefaultActivity::class.java)
            startActivity(intent)
            fromDefaultFragmentToMainActivity()
        }

        excerciseViewModel.latestWord.observe(this, Observer { excercise ->
            excercise?.let {
                excerciseAdapter.list.add(0, it)
                excerciseAdapter.notifyItemInserted(0)
            }
        })

        excerciseViewModel.allExcercises.observe(this, Observer { list ->
            excerciseAdapter.list.clear()
            excerciseAdapter.list.addAll(list)
            excerciseAdapter.notifyDataSetChanged()
        })

        excerciseViewModel.GetAllExcercise()


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        // View(RecyclerView)와 Data 를 연결 하기 위해서 Adapter 를 만들 꺼야.
        excerciseAdapter = ExcerciseAdapter(mutableListOf(), excerciseViewModel)
        binding.recyclerView.apply {
            adapter = excerciseAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            // RecyclerView 만들 때 : Adapter 연결 하기 && layoutManager 추가 하기
            val dividerItemDecoration =
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
//        Thread {
//            val list = ExcerciseDatabase.getInstance(this)?.excerciseDao()?.getAll() ?: emptyList()
//        val list = excerciseViewModel.GetAllExcercise()
//
//        // ExcerciseDatabase.getInstance(this) : MainActivity 와 ExcerciseDatabase 객체를 연결
//        // DataBase 에서 내용을 부르는 거야
//
//        excerciseAdapter.list.addAll(list)
//        //Adapter 의 list 에 Data 를 넣어 준거야.
        runOnUiThread {
            excerciseAdapter.notifyDataSetChanged()

            // Data 넣었 다고 알려 주는 거야 , notifyDataSetChanged() 에 의해서 UI가 변경이 될 것이 므로 Thread 를 만들어 줘야 해.
        }
//        }.start()
    }

    // 리팩토링 1
    private fun updateAddWord() { // // roomDatabase에 최근에 추가된 Data 가져와서 Adapter에 새로운 Data로 Collection에 넣어줘
        Thread {
            ExcerciseDatabase.getInstance(this)?.excerciseDao()?.getLatestWord()?.let { excercise ->
                excerciseAdapter.list.add(0, excercise.toExcerciseModel()) //Update 할 때 Data 를 추가 하고
                runOnUiThread {
//                    excerciseAdapter.notifyDataSetChanged()
                    excerciseAdapter.notifyItemInserted(0)
                }
            }
        }.start()
    }


    private fun deleteAll() {
        ExcerciseDatabase.getInstance(this)?.excerciseDao()?.deleteAll()
    }


    private fun fromDefaultFragmentToMainActivity() {
        val intent = Intent().getBooleanExtra("endDefaultFragment", false)
        if (intent) {
            initRecyclerView()
        }
    }


//    override fun onItemDeleteClick(excerciseModel: ExcerciseModel) {
////        Thread {
////            ExcerciseDatabase.getInstance(this)?.excerciseDao()?.delete(excerciseModel)
////            runOnUiThread {
////                initRecyclerView()
////            }
////        }.start()
////        initRecyclerView()
//
//        excerciseViewModel.DeleteExcercise(excerciseModel)
//        initRecyclerView()
//
//
//    }

}
