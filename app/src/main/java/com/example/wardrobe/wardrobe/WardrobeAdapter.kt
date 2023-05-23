package com.example.wardrobe.wardrobe

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wardrobe.FIRESTORE_TOP
import com.example.wardrobe.databinding.ItemviewWardrobeBinding
import com.example.wardrobe.wardrobe.model.WardrobeItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class WardrobeAdapter(
    private val itemClickEvent: (Int) -> Unit
) : ListAdapter<WardrobeItem, WardrobeAdapter.ViewHolder>(WardrobeItemDiffCallback) {

    private val firestore by lazy { Firebase.firestore }
    private val storage by lazy { Firebase.storage }
    private var checkedPosition = -1

    class ViewHolder(
        private val binding: ItemviewWardrobeBinding,
        db: FirebaseFirestore,
        private val storage: FirebaseStorage
    ) : RecyclerView.ViewHolder(binding.root) {

        // Top(상의) Collection Ref
        val topColRef = db.collection(FIRESTORE_TOP)

        fun bind(item: WardrobeItem, itemClickEvent: (Int) -> Unit) {
            val imageRef = storage.reference.child(item.dbClothesImageRefUrl)
            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                Glide.with(itemView.context).load(bmp).into(binding.ivClothes)
            }.addOnFailureListener {
                Log.e("", "firebase storage called failed")
            }


//            if (viewModel.isCodiMode.value == true)
//                checkBox.visibility = View.VISIBLE
//            else
//                checkBox.visibility = View.GONE
//            checkBox.isChecked = pos == checkedPosition


            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
//                    checkedPosition = pos
//                    viewModel.topSelectedCheckBox.value = pos
//                    notifyDataSetChanged()
                } else {
//                    if (pos == checkedPosition) {
//                        viewModel.topSelectedCheckBox.value = null
//                        checkedPosition =
//                            -1 // if the currently checked checkbox is unchecked manually
//                    }
                }
            }

            itemView.setOnClickListener {
//                topColRef.whereEqualTo("imageRef", viewModel.topItems[pos].clothesImageUrl).get()
//                    .addOnSuccessListener {
//                        for (doc in it) {
//                            itemClickEvent(adapterPosition)
//                            val bundle = Bundle()
//                            bundle.putString("imageRef", doc["imageRef"].toString())
//                            bundle.putBoolean("isTop", true)
//                            fragment.findNavController().navigate(
//                                R.id.action_wardrobeFragment_to_detailClothesFragment,
//                                bundle
//                            )
//                        }
//                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemviewWardrobeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding, firestore, storage)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], itemClickEvent)
    }
}