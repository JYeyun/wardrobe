package com.example.wardrobe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wardrobe.DTO.TopBottomDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Item(val clothesImageUrl: String)

class WardrobeViewModel: ViewModel() {
    val db = Firebase.firestore
    val topColRef = db.collection("top")
    // Bottom(하의) Collection Ref
    val bottomColRef = db.collection("bottom")

    val wardrobeItems = ArrayList<Item>()
    val wardrobeItemsListData = MutableLiveData<ArrayList<Item>>()

    val topItems = ArrayList<Item>()
    val topItemsListData = MutableLiveData<ArrayList<Item>>()

    val bottomItems = ArrayList<Item>()
    val bottomItemsListData = MutableLiveData<ArrayList<Item>>()

    val setItems = ArrayList<Item>()
    val setItemsListData = MutableLiveData<ArrayList<Item>>()

    val communityItems = ArrayList<Item>()
    val communityItemsListData = MutableLiveData<ArrayList<Item>>()


    val topSelectedCheckBox = MutableLiveData<Int>()
    val bottomSelectedCheckBox = MutableLiveData<Int>()

    val isCodiMode = MutableLiveData<Boolean>(false)

    val searchItems = ArrayList<Item>()
    val searchItemsListData = MutableLiveData<ArrayList<Item>>()
    private val articleDataList = mutableListOf<TopBottomDTO>()
    private val _articleLiveData = MutableLiveData<List<TopBottomDTO>>()
    val articleLiveData: LiveData<List<TopBottomDTO>> get() = _articleLiveData
    val searchSuccess = MutableLiveData(false)

    /* 옷장 이미지 */

    fun addWardrobeItem(item: Item,which: String){
        if(which.equals("top")){
            topItems.add(item)
            topItemsListData.value = topItems
        }
        else if(which.equals("bottom")){
            bottomItems.add(item)
            bottomItemsListData.value = bottomItems
        }
        else if(which.equals("set")){
            setItems.add(item)
            setItemsListData.value = setItems
        }
        else if(which.equals("season")){
            searchItems.add(item)
            searchItemsListData.value = searchItems
        }
//        wardrobeItems.add(item)
//        wardrobeItemsListData.value = wardrobeItems
    }

    fun searchItem(item: Item,which: String){

    }


    fun deleteAllWardrobeItem(which: String){
        if(which.equals("top")){
            topItems.clear()
            topItemsListData.value?.clear()
        }
        else if(which.equals("bottom")){
            bottomItems.clear()
            bottomItemsListData.value?.clear()
        }
        else if(which.equals("set")){
            setItems.clear()
            setItemsListData.value?.clear()
        }

//        wardrobeItems.clear()
//        wardrobeItemsListData.value?.clear()
    }

    fun deleteWardrobeItem(pos: Int){
        wardrobeItems.removeAt(pos)
        wardrobeItemsListData.value = wardrobeItems
    }


    /* 커뮤니티 이미지 */

    fun addCommunityItem(item: Item){
        communityItems.add(item)
        communityItemsListData.value = wardrobeItems
    }

//    fun searchArticleModel(title: String) {
//        searchItems.clear()
//        searchItemsListData.value?.clear()
//        for (TopBottomDTO in articleDataList) {
//            if (TopBottomDTO.season.equals(title)) {
//                searchItems.add(TopBottomDTO)
//                searchItemsListData.value = searchItems
//            }
//        }
//        _articleLiveData.value = searchItemsListData.value
//        searchSuccess.value = true
//    }
}