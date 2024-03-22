package com.company.health_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.company.health_app.databinding.ItemExcerciseBinding


class ExcerciseAdapter (
    val list : MutableList<Excercise>,
    private var widgetClickListener: ExcerciseItemClickListener,
) : RecyclerView.Adapter<ExcerciseAdapter.ExcerciseViewHolder>(){


    class ExcerciseViewHolder(private var binding : ItemExcerciseBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(excercise : Excercise) {
            binding.apply{
                itemName.text = excercise.name
                itemSetNum.text = excercise.setNum.toString()
                var currentSetNum = excercise.setNum.toString().toInt()

                discountSet.setOnClickListener {
                    if(currentSetNum > 0) {
                        currentSetNum -= 1
                        itemSetNum.text = currentSetNum.toString()
                        excercise.setNum = currentSetNum
                        Thread{
                            AppDatabase.getInstance(root.context)?.excerciseDao()?.update(excercise)
                        }.start()
                    }
                }
                deleteSet.setOnClickListener{

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
        holder.bind(excercise)
        holder.itemView.findViewById<Button>(R.id.deleteSet).setOnClickListener {
            widgetClickListener.onItemDeleteClick(excercise)
        }


    }

    interface ExcerciseItemClickListener {
        fun onItemDeleteClick(excercise: Excercise)
    }
}