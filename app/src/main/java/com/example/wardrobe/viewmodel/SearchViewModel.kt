package com.example.wardrobe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wardrobe.DTO.TopBottomDTO
import com.example.wardrobe.Login.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SearchViewModel : ViewModel() {

    private val database = Firebase.database
    private val myArticle = database.getReference("top")
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val userRef = Firebase.database.getReference("user")

 //   private var followingUidList = mutableListOf<String>()
 //   private var followingTotalDataList = mutableListOf<FollowerData>()
    private val myArticleDataList = mutableListOf<TopBottomDTO>()
    private val followerDataList = mutableListOf<TopBottomDTO>()
    private val articleDataList = mutableListOf<TopBottomDTO>()
    private val searchArticleDataList = mutableListOf<TopBottomDTO>()

    private val _articleLiveData = MutableLiveData<List<TopBottomDTO>>()
    val articleLiveData: LiveData<List<TopBottomDTO>> get() = _articleLiveData

  //  private val _followingUidLiveData = MutableLiveData<List<String>>()
  //  val followingUidLiveData: LiveData<List<String>> get() = _followingUidLiveData

 //   private val _followingTotalDataLiveData = MutableLiveData<List<FollowerData>>()
  //  val followingTotalDataLiveData: LiveData<List<FollowerData>> get() = _followingTotalDataLiveData

    val searchSuccess = MutableLiveData(false)
    val editProfileSuccess = MutableLiveData<Boolean?>(null)

    val isLoading = MutableLiveData(true)

    init {
        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val articleModel = snapshot.getValue(TopBottomDTO::class.java)
                articleModel ?: return

                addArticleModel(articleModel)
                updateMyArticle()
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }

        myArticle.addChildEventListener(listener)

        userRef.child(firebaseAuth.currentUser?.uid!!).child("following").get()
            .addOnSuccessListener {
                val followingList = it.value as List<String>

                for (following in followingList) {
                    if (following == "don't touch this key") continue

                    userRef.child(following).get().addOnSuccessListener { data ->
                        val followerData = data.getValue(UserDTO::class.java)
                   //     followingTotalDataList.add(FollowerData(followerData?.nickname ?: "", followerData?.email ?: "", followerData?.uid ?: ""))

                   //     _followingTotalDataLiveData.value = followingTotalDataList
                    }
                }

            //    _followingUidLiveData.value = followingList
            }
    }

    fun updateMyArticle() {
        Log.d("yousong","몇번")
        for (articleModel in articleDataList) {
            if (articleModel.userID== firebaseAuth.currentUser?.uid && !myArticleDataList.contains(articleModel)) {
                addMyArticleModel(articleModel)
            } else {
                //deleteArticleModel(model)
            }
        }
    }

    fun initHomeFragmentData() {
        _articleLiveData.value = articleDataList
    }

    fun initFollowerFragmentData() {
        _articleLiveData.value = followerDataList
    }

    fun initMyArticleData() {
        _articleLiveData.value = myArticleDataList
        Log.d("eunseo","여기가 불려요오오오옹오ㅓ ")
    }

    fun addArticleModel(model: TopBottomDTO) {
        articleDataList.add(model)
        _articleLiveData.value = articleDataList
    }

    fun searchArticleModel(title: String) {
        searchArticleDataList.clear()
        for (articleModel in articleDataList) {
            if (articleModel.season.equals(title)) {
                searchArticleDataList.add(articleModel)
            }
        }
        _articleLiveData.value = searchArticleDataList
        searchSuccess.value = true
    }
    fun addMyArticleModel(model: TopBottomDTO) {
        myArticleDataList.add(model)
    }


}