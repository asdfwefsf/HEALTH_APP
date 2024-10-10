package com.company.health_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.databinding.ActivityMainBinding
import com.company.health_app.presentation.difault.DefaultActivity
import com.company.health_app.presentation.viewmodel.ExcerciseViewModel
import com.google.android.datatransport.BuildConfig
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var excerciseAdapter: ExcerciseAdapter
    private lateinit var excerciseDao: ExcerciseDao

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

    // 결제 관련 테스트 //

    //    private var billingClient : BillingClient? = null
//    var response : String? = null
//    var des : String? = null
//    var sku : String? = null
    var isSucess = false


    // 결제 관련 테스트 //

    // 인앱 업데이트
    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.FLEXIBLE
//    private val filexibleUpdateType = AppUpdateType.FLEXIBLE
//    private val immediateUpdateType = AppUpdateType.IMMEDIATE
    // 인앱 업데이트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 앱 업데이트 매니저
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        // 앱 업데이트를 확인하려고 할 때 사용하는 목적 객체
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Check for update availability
        // 앱의 업데이트를 요청하기 전에는 , 너의 앱의 업데이트 가능한 버전이 있는지 체크해라.
        // 업데이트에 대한 정보를 확인하기 위해서는 AppUpdateManager를 사용해라
        // 앱 업데이트의 가능 여부를 체크할 때는 AppUpdateManager를 사용하여라.!
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {


//                Toast.makeText(applicationContext, "업데이트 즉각 반응 !!", Toast.LENGTH_SHORT).show()
                Log.d("sfjlsjfsflji1415" , "업데이트 즉각 반응 !!")
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {

                Log.d("sfjlsjfsflji1415" , "업업데이트 천천히 하소 !!")

//                Toast.makeText(applicationContext, "업데이트 천천히 하소 !!", Toast.LENGTH_SHORT).show()
            } else if (appUpdateInfo.updateAvailability() != UpdateAvailability.UPDATE_AVAILABLE) {
                Log.d("sfjlsjfsflji1415" , "업데이트 할 것 없음 !!")


//                Toast.makeText(applicationContext, "업데이트 할 것 없음 !!", Toast.LENGTH_LONG).show()
            }
        }

        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.registerListener(installStateUpdatedListener)
        } else if(updateType == AppUpdateType.FLEXIBLE) {
            Toast.makeText(
                applicationContext,
                "앱의 업데이트가 가능합니다. , Dialog로 커버할 예정임.",
                Toast.LENGTH_SHORT
            ).show()
        }

//        // 업데이트 관련 체크 함수 호출
        checkForAppUpdates()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 결제 관련 테스트 //
        // 결제(1) : 결제 관련 메인 인터페이스인 BillingClient의 객체를 생성 및 초기화한다.!
        // _billingClient => 구글 플레이 결제 라이브러리와 내 앱 사이의 통신을 시켜주는 메인 인터페이스이다.
        //               => 동기 , 비동기 적인 편리한 메서드를 제공해준다.
        //               => 하나의 활성화 된 BillingClient 연결을 갖는 것을 추천해준다 : 하나의 이벤트에서 많은 PurchasedUpdatadListener 콜백을 막기 위해서.
        // _newBuilder()를 사용하여 BillingClient를 생성 할 수 있으며 , newBuilder()의 paramter에 어떠한 Context든 넣을 수 있는데 ,
        // BillingClient는 전달 받은 Context를 사용하여 애플리케이션 컨텍스트를 얻을 수 있기 때문에 메모리 누수를 걱정 할 필요 X
        // _구매 정보에 대한 업데이트를 받기 위해서는 무조건 setListener()를 호출해야 한다 , 이때 파라미터에 purchaseUpdatedListener를 넣어주어
        // billingClient가 purchaseUpdatadListener를 참조 할 수 있게 해주어야 한다. -> purchaseUpdatedListener : 앱이 모든 구매 정보의 업뎃을 받을 수 있다.
//        billingClient = BillingClient.newBuilder(this)
//            .setListener(purchasesUpdatedListener)
//            .enablePendingPurchases()
//            .build()


        // 이 버튼 누르면 결제 위에서 생성한 BillingClient의 객체가 구글 플레이와 연결한다.
//        binding.bilingTest.setOnClickListener {
//
//            // 결제(2) : 구글 플레이와 연결하기 -> BillingClient 객체 생성 및 초기화 한 이후에  구글 플레이와 연결을 설정해야 한다.
//            // _구글 플레이와 연결하기 위해서는 startConnection()을 호출해야 한다.
//            // _해당 연결 과정은 비동기적으로 진행되며 , 클라이언트 설정이 완료되고 , 다음 요청을 받을 준비가 되면 콜백을 받기 위해서
//            // BillingClicStateListenr를 구현해야 한다.
//            // _구글 플레이와 연결이 끊겼을 때를 처리하기 위해서 재연결 로직을 구현행하 한다.
//            // _재연결 로직을 구현 할 때에는 obBillingServiceDisconnected() 콜백 메서드를 오버라이드 해야 하며,
//            // BillingClient 객체가 startConnection()을 호출해서 다시 구글 플레이와 연결되었는지 확인하고 추가 요청을 해야 한다.
//            billingClient!!.startConnection(object : BillingClientStateListener {
//
//                // 결제 서비스 연결 중간에 끊김
//                override fun onBillingServiceDisconnected() {
//                    // 여기서 에러 처리 하는 거임 : 공식 문서에서는 연결 끊기면 다시 연결 시키라고 되어 있음.
//                    // 물론 유저한테 알려줘야 함. (재연결 로직이라고 표현하였음.)
//                }
//
//                // 결제 서비스 연결 세팅 완료 되면 (성공했다는 의미임) : Google Play ~ BillingClien ~ Rest Of App(앱) => 이 3가지 요소들이 연결되었다는 의미임.
//                override fun onBillingSetupFinished(billingResult : BillingResult) {
//
//                    // 플레이 콘솔에 지정된 상품의 아이디 값을 가져와서 해당 상품을 INAPP||SUBS 둘 중 하나로 선택하면 됨.
//                    val productList = listOf(QueryProductDetailsParams.Product.newBuilder()
//                        .setProductId("subcriptiontest123")
//                        // ProductType.INAPP : 앱 내 앱 상품 ( 1회성 결제 )
//                        // ProductType.SUBS : 앱 구독 상품 ( 구독 결제 )
//                        .setProductType(BillingClient.ProductType.SUBS)
//                        .build()
//                    )
//
//                    Log.d("sflsfjlsfjlsjflsjflsijfe" , productList.toString())
//
//                    // 상품 세부 정보에 대한 정보가 들어있음.
//                    val params = QueryProductDetailsParams.newBuilder()
//                        .setProductList(
//                            productList
//                        )
//
//                    // 바로 위에 params에서의 정보를 쿼리(조사하는 느낌)해서 람다로 "결제 여부" , "상품 리스트" 반환
//                    billingClient!!.queryProductDetailsAsync(params.build()) {
//                        billingResult , productDetailList ->
//
//                        // 상품 세부 정보에 대한 쿼리가 성공했냐 못했냐 성공하면 Ok , 그외의 것들은 else로 처리 -> case 다양해서 원하는대로 처리가능
//                        if(billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                            if (productDetailList.isEmpty()) {
//
//                                Log.d("sflsjf" , productDetailList.toString())
//                            }
//                        } else {
//                            Log.d("sflsjf" , productDetailList.toString())
//
//                        }
//
//
//
//
//
//                        for(productDetails in productDetailList) {
//
//                            Log.d("sflsjfslefnsl1" , billingResult.toString())
//                            Log.d("sflsjfslefnsl1" , productDetailList.toString())
//                            // 첫뻔째 토큰값가져옴 , 어떤걸 했는지 알아야되니깐 +
//                            // 구독은 하나만 결제하면 되서 걍 하나만가져와도된다 . 1회성 여러개 결제하면
//                            // 결제하는 갯수만큼 가져오면 된다. 리스트로 가져와서 아래에서 사용하면 좋음.
//                            val offerToken = productDetails.subscriptionOfferDetails!!.get(0)
//                                .offerToken
//
//
//                            // 결제상품리스트로서 여러 상품을 한번에 결정할 수 잇게 리스트로 맞들어줘야된다.
//                            // 만약 1회성 결제로 여러개 구매하는 경우에만
//                            val productDetailsParamsList =
//                                listOf(
//                                    offerToken.let {
//                                        BillingFlowParams.ProductDetailsParams.newBuilder()
//                                            .setProductDetails(productDetails)
//                                            .setOfferToken(it)
//                                            .build()
//                                    }
//                                )
//
//                            // 이게 찐 결제 시스템을 일으키는 녀석
//                            val billingFlowParams = BillingFlowParams.newBuilder()
//                                .setProductDetailsParamsList(productDetailsParamsList)
//                                .build()
//
//                            // 결제가 시작됨 . 결제 성공에 따라서 아래에 설정된 purchasesUpdatedListener가 호출됨.
//                            val billingResult_ = billingClient!!.launchBillingFlow(this@MainActivity , billingFlowParams)
//
//                            Log.d("bsfsfdsf" , billingResult_.responseCode.toString())
//                        }
//
//
//                    }
//                }
//            })
//        }


        // 결제 관련 테스트 //

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



    // 결제 관련 테스트 //

    // 결제가 업데이트 되었을 때 호출되는 리스너임 : 코드 값에 따라서 개발자가 로직을 맘대로 작성 할 수 있음.
//    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult , Purchase ->
//
//        // 결제 ok
//        if(billingResult.responseCode == BillingClient.BillingResponseCode.OK && Purchase != null) {
//            for (purchase in Purchase) {
//
//                HandlePurchase(purchase)
//
//            }
//        }
//        // 이미 구독중임
//        else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
//            Toast.makeText(
//                this,
//                "이미 구독",
//                Toast.LENGTH_SHORT
//            ).show()
//            isSucess = true
//
//        }
//        // 결제수단정보같은것이 틀리면
//        else if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
//            Toast.makeText(
//                this,
//                "결제수단이이상해",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        // 결제 에러뜸
//        else {
//            Toast.makeText(
//                this,
//                "결제에러 : ${billingResult.debugMessage}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    private fun HandlePurchase(purchase: Purchase) {
//        // 결제한걸 사용하기 위해서 구매토큰을 사용해서 소비토큰을 만듦
//        val consumeParams = ConsumeParams.newBuilder()
//            .setPurchaseToken(purchase.purchaseToken)
//            .build()
//
//        val listener = ConsumeResponseListener { billingResult , s ->
//
//            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                // 결제 햇어 ? 응 했어 => OK 여기서 구매한 컨텐츠를 사용하고 싶을 때마다
//                // 여기서 이제 구매 만료 기간같은것을 백에서 받아와서 만료되면
//
//                // 임의의 값 : Boolean = true 이거를 false로 바꾸면 false로 바꾸어진 순간 서버로 보내든가 , 로컬에서 처리하든가 해서 딱 false를 보내면 이제 거기서 저장도 하고
//                // 이제 false니까 그때부터 해당 상품을 사용하지 못하도록 처리를 하면 된다.
//            }
//
//        }
//
//        billingClient!!.consumeAsync(consumeParams , listener)
//
//        if(purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
//            // 여기서 이제 토큰 값을 "안전한 서버"로 보내서 사용하면됨.
//
//        }
//
//
//    }

    // 결제 관련 테스트 //

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


    // 인앱 업데이트
    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(
                applicationContext,
                "Download successful. Restarting app in 5 seconds",
                Toast.LENGTH_SHORT
            ).show()
            lifecycleScope.launch {
                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
        }
    }


    private fun checkForAppUpdates() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->

            val nowAppVersionCode = BuildConfig.VERSION_CODE
            val newAppVersionCode = info.availableVersionCode()

            if(newAppVersionCode - nowAppVersionCode >= 2) {
                // 강제업데이트 시키는 코드
                //

                val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                val isUpdateAlloewd = when (updateType) {
                    AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                    AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                    else -> false
                }

                if (isUpdateAvailable && isUpdateAlloewd) {
                    // 실제 업데이트를 트리거 하는 코드
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
                            if (result.resultCode != RESULT_OK) {
                                Log.d(
                                    "Update flow",
                                    "Update flow failed! Result code : " + result.resultCode
                                )

                            }
                        },
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )
                }
            } else {
                val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                val isUpdateAlloewd = when (updateType) {
                    AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                    AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                    else -> false
                }
                if (isUpdateAvailable && isUpdateAlloewd) {
                    // 업데이트  알려만준다.
                    Toast.makeText(
                        applicationContext,
                        "앱의 업데이트가 가능합니다. , Dialog로 커버할 예정임.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    //    // 인앱 업데이트
//
    override fun onResume() {
        super.onResume()
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        this,
                        123
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode != RESULT_OK) {
                println("Something went wrong updating...")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }
}
