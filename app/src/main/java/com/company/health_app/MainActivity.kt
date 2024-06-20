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
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.databinding.ActivityMainBinding
import com.company.health_app.presentation.difault.DefaultActivity
import com.company.health_app.presentation.viewmodel.ExcerciseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var excerciseAdapter: ExcerciseAdapter
    private lateinit var  excerciseDao : ExcerciseDao
    // 전체 데이터 삭제 여부
    private val deleteAllLiveData = MutableLiveData<Boolean>()
    private val excerciseViewModel: ExcerciseViewModel by viewModels()
    // ExcerciseAddActivity 종료되면 호출
    private val newRoutine = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val updated = result.data?.getBooleanExtra("updated", false) ?: false
        if (result.resultCode == RESULT_OK && updated) {
            updateAddWord()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 탑바 UI Text
        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }

        initRecyclerView()

        //전체 데이터 삭제되면 오늘 운동 끝 토스트 메시지 + 화면 렌더링
        deleteAllLiveData.observe(this) { isDeleted ->
            if (isDeleted) {
                initRecyclerView()
                Toast.makeText(this, "오늘 운동 끝!", Toast.LENGTH_SHORT).show()
            }
        }

        // 기본 운동 추가 로직 시작 버튼
        binding.goToDefaultActivityButton.setOnClickListener {
            val intent = Intent(this, DefaultActivity::class.java)
            // Intent()의 생성자가 Activity 객체가 아닌 클래스 객체를 요구하기 때문에 , ::class 를 사용하여 KClass 객체로 변경
            // Intent.java가 자바 클래스이기 때문에 .java를 사용하여 KClass 객체를 자바 클래스 객체로 변경
            startActivity(intent)
            fromDefaultFragmentToMainActivity()
        }

        // 전체 데이터 삭제 로직 시작 버튼
        binding.endButton.setOnClickListener {
            excerciseViewModel.deleteAll()
            // 삭제되면
            deleteAllLiveData.postValue(true)
        }
        // 커스텀 운동 목록 추가 로직 시작 버튼
        binding.callExcerciseAdd.setOnClickListener {
            Intent(this, ExcerciseAddActivity::class.java).let {
                newRoutine.launch(it)
            }
        }

        excerciseViewModel.allExcercises.observe(this, Observer { list ->
            excerciseAdapter.list.clear()
            excerciseAdapter.list.addAll(list)
            excerciseAdapter.notifyDataSetChanged()
        })

        excerciseViewModel.getAllExcercise()

    }
    // 기본운동 추가 로직 종료되면 DefaultFrament에서 Intent로 값 받아와서 true이면 initRecyclerView() 실행
    private fun fromDefaultFragmentToMainActivity() {
        val intent = Intent().getBooleanExtra("endDefaultFragment", false)
        if (intent) {
            initRecyclerView()
        }
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
        runOnUiThread {
            excerciseAdapter.notifyDataSetChanged()
        }
    }

    // ExcerciseAddActivity가 종료되면 호출되는 함수에서 제일 마지막에 호출 :
    private fun updateAddWord() {
        Thread {
            excerciseViewModel.getAllExcercise()
        }.start()
    }
}
