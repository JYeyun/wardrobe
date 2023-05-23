package com.example.wardrobe.wardrobe

import androidx.recyclerview.widget.DiffUtil
import com.example.wardrobe.wardrobe.model.WardrobeItem

object WardrobeItemDiffCallback : DiffUtil.ItemCallback<WardrobeItem>() {

    override fun areItemsTheSame(oldItem: WardrobeItem, newItem: WardrobeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WardrobeItem, newItem: WardrobeItem): Boolean {
        return oldItem == newItem
    }
}