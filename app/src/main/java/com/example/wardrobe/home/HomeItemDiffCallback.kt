package com.example.wardrobe.home

import androidx.recyclerview.widget.DiffUtil
import com.example.wardrobe.home.model.HomeItem

object HomeItemDiffCallback : DiffUtil.ItemCallback<HomeItem>() {

    override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }
}