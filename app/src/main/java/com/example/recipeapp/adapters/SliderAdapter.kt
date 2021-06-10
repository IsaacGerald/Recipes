package com.example.recipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipeapp.databinding.SliderItemBinding
import com.example.recipeapp.models.Slider
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val sliders: List<Slider>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun getCount(): Int {
        return sliders.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        return SliderViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SliderViewHolder?, position: Int) {
        val item = sliders[position]
        holder!!.bind(item)
    }

    class SliderViewHolder(private val binding: SliderItemBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

        fun bind(item: Slider) {
            binding.slider = item
            binding.invalidateAll()
        }


        companion object {
            fun from(parent: ViewGroup?): SliderViewHolder {
                val inflater = LayoutInflater.from(parent!!.context)
                val view = SliderItemBinding.inflate(inflater, parent, false)
                return SliderViewHolder(view)
            }
        }

    }
}