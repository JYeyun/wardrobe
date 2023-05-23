package com.example.wardrobe.wardrobe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wardrobe.FIRESTORE_BOTTOM
import com.example.wardrobe.FIRESTORE_TOP
import com.example.wardrobe.FIRESTORE_USER_ID
import com.example.wardrobe.R
import com.example.wardrobe.TEST_CURRENT_UID
import com.example.wardrobe.base.BaseFragment
import com.example.wardrobe.databinding.FragmentWardrobeBinding
import com.example.wardrobe.wardrobe.model.WardrobeItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WardrobeFragment : BaseFragment<FragmentWardrobeBinding>(R.layout.fragment_wardrobe) {
    protected lateinit var navController: NavController

    private val viewModel by viewModels<WardrobeViewModel>()


    private val db by lazy { Firebase.firestore }

    // Top(상의) Collection Ref
    private val topColRef by lazy { db.collection(FIRESTORE_TOP) }

    // Bottom(하의) Collection Ref
    private val bottomColRef by lazy { db.collection(FIRESTORE_BOTTOM) }

    private val wardrobeTopAdapter by lazy { WardrobeAdapter() {} }
    private val wardrobeBottomAdapter by lazy { WardrobeAdapter() {} }

    companion object {
        const val TOP_REF = "topRef"
        const val BOTTOM_REF = "bottomRef"
        const val IMAGE_REF = "imageRef"
    }

    override fun initView() {
        bind {
            topAdapter = wardrobeTopAdapter
            bottomAdapter = wardrobeBottomAdapter
        }

        loadTopList()

        initUI()
        observeData()
    }

    private fun makeRefBundle(): Bundle {
        return Bundle().apply {
            putString(
                TOP_REF,
                viewModel.topItemListData.value!![viewModel.topSelectedCheckBox.value!!].dbClothesImageRefUrl
            )
            putString(
                BOTTOM_REF,
                viewModel.bottomItemListData.value!![viewModel.bottomSelectedCheckBox.value!!].dbClothesImageRefUrl
            )
        }
    }

    private fun observeData() {
        viewModel.topItemListData.observe(viewLifecycleOwner) {
            wardrobeTopAdapter.submitList(it)
        }

        viewModel.bottomItemListData.observe(viewLifecycleOwner) {
            wardrobeBottomAdapter.submitList(it)
        }

        viewModel.isCodiMode.observe(viewLifecycleOwner) {

            // 무슨 코드인지 이해 못함
            when (binding.radioGroup.checkedButtonId) {
                R.id.button_top -> wardrobeTopAdapter
                R.id.button_bottom -> wardrobeBottomAdapter
            }

            val codiBtnImage = if (it) R.drawable.button_return else R.drawable.button_fab_codi
            binding.floatingActionButtonCodi.setImageResource(codiBtnImage)
        }

        viewModel.topSelectedCheckBox.observe(viewLifecycleOwner) {
            if (viewModel.topSelectedCheckBox.value == null) {
                binding.radioGroup.check(binding.buttonBottom.id)
                return@observe
            }
            if (viewModel.bottomSelectedCheckBox.value == null) return@observe
            navigateToCodi()
        }

        viewModel.bottomSelectedCheckBox.observe(viewLifecycleOwner) {
            if (viewModel.bottomSelectedCheckBox.value == null) {
                binding.radioGroup.check(binding.buttonTop.id)
                return@observe
            }
            if (viewModel.topSelectedCheckBox.value == null) return@observe

            navigateToCodi()
        }
    }

    private fun navigateToCodi() {
        findNavController().navigate(
            R.id.action_wardrobeFragment_to_doCodiFragment,
            makeRefBundle()
        )
    }

    private fun initUI() {
        var isFABOpen = false

        binding.floatingActionButton.setOnClickListener {

            isFABOpen = !isFABOpen
            binding.floatingActionButtonAdd.visibility =
                if (!isFABOpen) View.VISIBLE else View.INVISIBLE
            binding.floatingActionButtonCodi.visibility =
                if (!isFABOpen) View.VISIBLE else View.INVISIBLE

            startAnimation(
                view = binding.floatingActionButton,
                rotation = if (!isFABOpen) 45f else 0f,
                scaleX = 1.0f,
                scaleY = 1.0f,
                duration = 300
            )
        }

        binding.floatingActionButtonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_wardrobeFragment_to_addclothesFragment)
        }

        binding.floatingActionButtonCodi.setOnClickListener {
            viewModel.isCodiMode.value = viewModel.isCodiMode.value != true
        }

        binding.radioGroup.addOnButtonCheckedListener { group, _, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            when (group.checkedButtonId) {
                R.id.button_top -> loadTopList()
                R.id.button_bottom -> loadBottomList()
            }
        }
    }

    private fun startAnimation(
        view: View,
        rotation: Float,
        scaleX: Float,
        scaleY: Float,
        duration: Long
    ) {
        view.animate()
            .rotation(rotation)
            .scaleX(scaleX)
            .scaleY(scaleY)
            .setDuration(duration)
            .start()
    }

    private fun loadTopList() {
        binding.recyclerViewBottom.visibility = View.GONE
        binding.recyclerViewTop.visibility = View.VISIBLE
        viewModel.deleteAllWardrobeItem(FIRESTORE_TOP)
        topColRef.whereEqualTo(FIRESTORE_USER_ID, TEST_CURRENT_UID).get()
            .addOnSuccessListener {
                for (doc in it) {
                    viewModel.addWardrobeItem(
                        WardrobeItem(doc[IMAGE_REF].toString()),
                        FIRESTORE_TOP
                    )
                }
            }
    }

    private fun loadBottomList() {
        binding.recyclerViewTop.visibility = View.GONE
        binding.recyclerViewBottom.visibility = View.VISIBLE
        viewModel.deleteAllWardrobeItem(FIRESTORE_BOTTOM)
        bottomColRef.whereEqualTo(FIRESTORE_USER_ID, TEST_CURRENT_UID).get()
            .addOnSuccessListener {
                for (doc in it) {
                    viewModel.addWardrobeItem(
                        WardrobeItem(doc[IMAGE_REF].toString()),
                        FIRESTORE_BOTTOM
                    )
                }
            }
    }
}


