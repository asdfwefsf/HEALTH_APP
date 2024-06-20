package com.company.health_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.health_app.databinding.ItemExcerciseBinding
import com.company.health_app.domain.model.ExcerciseModel
import com.company.health_app.presentation.viewmodel.ExcerciseViewModel


class ExcerciseAdapter (
    val list : MutableList<ExcerciseModel>,
    private val excerciseViewModel: ExcerciseViewModel,
) : RecyclerView.Adapter<ExcerciseAdapter.ExcerciseViewHolder>(){

    class ExcerciseViewHolder(private var binding : ItemExcerciseBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(excersiceModel : ExcerciseModel , excerciseViewModel : ExcerciseViewModel) {
            binding.apply{
                itemName.text = excersiceModel.name
                itemSetNum.text = excersiceModel.setNum.toString()
                var currentSetNum = excersiceModel.setNum.toString().toInt()

                discountSet.setOnClickListener {
                    if(currentSetNum > 0) {
                        currentSetNum -= 1
                        itemSetNum.text = currentSetNum.toString()
                        excersiceModel.setNum = currentSetNum
                        excerciseViewModel.updateExcercise(excersiceModel)
                    }
                }
                deleteSet.setOnClickListener{
                    excerciseViewModel.deleteExcercise(excersiceModel)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcerciseViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemExcerciseBinding.inflate(inflater, parent, false)
        return ExcerciseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ExcerciseViewHolder, position: Int) {
        val excercise = list[position]
        holder.bind(excercise , excerciseViewModel)
    }

}