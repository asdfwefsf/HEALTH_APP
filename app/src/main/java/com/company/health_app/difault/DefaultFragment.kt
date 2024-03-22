package com.company.health_app.difault

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.company.health_app.AppDatabase
import com.company.health_app.Excercise
import com.company.health_app.R
import com.company.health_app.databinding.DefaultFragmentBinding
import com.company.health_app.databinding.DialogCountSettingBinding

class DefaultFragment : Fragment() {

    private lateinit var binding: DefaultFragmentBinding
    private lateinit var dialogBinding: DialogCountSettingBinding
    private var numSet: Int = 0
    private lateinit var excercise: Excercise
    private var nameToDelete: String = ""
    private var bodyPart1: String = ""
    private var bodyPart2: String = ""
    private var bodyPart3: String = ""
    private var bodyPart4: String = ""
    private var bodyPart5: String = ""
    private var bodyPart6: String = ""
    private var bodyPart7: String = ""
    private var position: Int = 0 // position 변수 추가


    companion object {
        fun newInstance(
            position: Int,
            bodyPart1: String,
            bodyPart2: String,
            bodyPart3: String,
            bodyPart4: String,
            bodyPart5: String,
            bodyPart6: String,
            bodyPart7: String
        ): DefaultFragment {
            val fragment = DefaultFragment()
            val args = Bundle()
            args.putInt("position", position)
            args.putString("bodyPart1", bodyPart1)
            args.putString("bodyPart2", bodyPart2)
            args.putString("bodyPart3", bodyPart3)
            args.putString("bodyPart4", bodyPart4)
            args.putString("bodyPart5", bodyPart5)
            args.putString("bodyPart6", bodyPart6)
            args.putString("bodyPart7", bodyPart7)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView( // onCreateViewHolder 느낌
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // 실제로 보여질 뷰

        binding = DefaultFragmentBinding.inflate(inflater, container, false)
        dialogBinding = DialogCountSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply {
            title = "어디 운동 할꾸?!"
        }

        val arguments = arguments
        if (arguments != null) {
            position = arguments.getInt("position", 0)
            bodyPart1 = arguments.getString("bodyPart1", "")
            bodyPart2 = arguments.getString("bodyPart2", "")
            bodyPart3 = arguments.getString("bodyPart3", "")
            bodyPart4 = arguments.getString("bodyPart4", "")
            bodyPart5 = arguments.getString("bodyPart5", "")
            bodyPart6 = arguments.getString("bodyPart6", "")
            bodyPart7 = arguments.getString("bodyPart7", "")
        }

        binding.textChip1.apply {
            text = bodyPart1
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setTitle("ddd")
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum1.text = numSet.toString()
                        excercise = Excercise(bodyPart1, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart1
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum1.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }

        binding.textChip2.apply {
            text = bodyPart2
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum2.text = numSet.toString()
                        excercise = Excercise(bodyPart2, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart2
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum2.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }

        binding.textChip3.apply {
            text = bodyPart3
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum3.text = numSet.toString()
                        excercise = Excercise(bodyPart3, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart3
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum3.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }

        binding.textChip4.apply {
            text = bodyPart4
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum4.text = numSet.toString()
                        excercise = Excercise(bodyPart4, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart4
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum4.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }

        binding.textChip5.apply {
            text = bodyPart5
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum5.text = numSet.toString()
                        excercise = Excercise(bodyPart5, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart5
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum5.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }

        binding.textChip6.apply {
            text = bodyPart6
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum6.text = numSet.toString()
//                        setNumList.add(numSet)
                        excercise = Excercise(bodyPart6, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart6
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum6.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }

        binding.textChip7.apply {
            text = bodyPart7
            setOnClickListener {
                val dialogView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.dialog_count_setting, null, false
                )
                val choiceSetPicker = dialogView.findViewById<NumberPicker>(R.id.choiceSetPicker)
                choiceSetPicker.maxValue = 10
                choiceSetPicker.minValue = 1
                choiceSetPicker.value = numSet

                AlertDialog.Builder(requireContext()).apply {
                    setMessage("몇 세트 하실겁니까")
                    setView(dialogView)
                    setPositiveButton("확인") { _, _ ->
                        numSet = choiceSetPicker.value.toString().toInt()
                        binding.setNum7.text = numSet.toString()
//                        setNumList.add(numSet)
                        excercise = Excercise(bodyPart7, numSet)
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()?.insert(
                                excercise
                            )
                        }.start()
                    }
                    setNegativeButton("취소") { _, _ ->
                    }
                    setNeutralButton("DB 삭제") { _, _ ->
                        nameToDelete = bodyPart7
                        Thread {
                            AppDatabase.getInstance(requireContext())?.excerciseDao()
                                ?.deleteByName(nameToDelete)
                        }.start()
                        binding.setNum7.text = ""
                    }
                }.show().apply {
                    findViewById<TextView>(android.R.id.button1)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button2)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                    findViewById<TextView>(android.R.id.button3)?.apply {
                        textSize = 14f
                        setTextColor(context.getColor(R.color.white))
                    }
                }
            }
        }



        binding.endFragment.setOnClickListener {
            callDefaultActivityToNotifyEndDefaultFragment()
        }

    }

    // UI UPDATE
    private fun callDefaultActivityToNotifyEndDefaultFragment() {
        val intent = Intent(requireActivity(), DefaultActivity::class.java)
        intent.putExtra("endDefaultFragment", true)
        startActivity(intent)
    }

}

