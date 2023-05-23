package com.example.wardrobe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wardrobe.adapters.WardrobeRecyclerViewAdapter
import com.example.wardrobe.databinding.FragmentSearchBinding
import com.example.wardrobe.viewmodel.Item
import com.example.wardrobe.viewmodel.WardrobeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment: Fragment(){
    private lateinit var binding:FragmentSearchBinding
    private val viewModel by viewModels<WardrobeViewModel> ()
    private lateinit var adapter:WardrobeRecyclerViewAdapter

    val currentUID = "3t6Dt8DleiZXrzzf696dgF15gJl2"

    val db = Firebase.firestore
    // Top(상의) Collection Ref
    val topColRef = db.collection("top")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }
    private fun initView(){
        adapter = WardrobeRecyclerViewAdapter(viewModel,context,this)

        viewModel.topItemsListData.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
        }
//        loadTopList()
        binding.SearchRecyclerView.apply{
            adapter=adapter
            layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        }
        binding.searchProductBtn.setOnClickListener {
            Log.d("search", "input: ${binding.editSearch.text.toString()}")
            viewModel.searchArticleModel(binding.editSearch.text.toString())
        }

    }
//    private fun loadTopList(){
//        viewModel.deleteAllWardrobeItem("top")
//        topColRef.whereEqualTo("userID",currentUID).get()
//            .addOnSuccessListener {
//                for(doc in it){
//                    viewModel.addWardrobeItem(Item(doc["imageRef"].toString()),"top")
//                }
//            }
//    }

}