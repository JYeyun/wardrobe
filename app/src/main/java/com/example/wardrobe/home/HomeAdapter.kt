package com.example.wardrobe.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wardrobe.databinding.ItemviewHomeBinding
import com.example.wardrobe.home.model.HomeItem

class HomeAdapter() :
    ListAdapter<HomeItem, HomeAdapter.ViewHolder>(HomeItemDiffCallback) {

    class ViewHolder(private val binding: ItemviewHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeItem) {

            // clothesImage 세팅
            Glide.with(itemView).load(item.clothesImageUrl).into(binding.ivClothes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemviewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}