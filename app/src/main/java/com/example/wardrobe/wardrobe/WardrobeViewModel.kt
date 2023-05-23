package com.example.wardrobe.wardrobe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wardrobe.wardrobe.model.WardrobeItem

class WardrobeViewModel : ViewModel() {
    private val wardrobeItemList = mutableListOf<WardrobeItem>()
    private val _wardrobeItemListData = MutableLiveData<List<WardrobeItem>>()
    val wardrobeItemListData: LiveData<List<WardrobeItem>> = _wardrobeItemListData

    private val topItemList = mutableListOf<WardrobeItem>()
    private val _topItemListData = MutableLiveData<List<WardrobeItem>>()
    val topItemListData: LiveData<List<WardrobeItem>> = _topItemListData

    private val bottomItemList = mutableListOf<WardrobeItem>()
    private val _bottomItemsListData = MutableLiveData<List<WardrobeItem>>()
    val bottomItemListData: LiveData<List<WardrobeItem>> = _bottomItemsListData

    private val setItemList = mutableListOf<WardrobeItem>()
    private val _setItemListData = MutableLiveData<List<WardrobeItem>>()
    val setItemListData: LiveData<List<WardrobeItem>> = _setItemListData

    private val communityItemList = mutableListOf<WardrobeItem>()
    private val _communityItemListData = MutableLiveData<List<WardrobeItem>>()
    val communityItemListData: LiveData<List<WardrobeItem>> = _communityItemListData

    val topSelectedCheckBox = MutableLiveData<Int>()
    val bottomSelectedCheckBox = MutableLiveData<Int>()

    val isCodiMode = MutableLiveData<Boolean>(false)

    /* 옷장 이미지 */

    fun addWardrobeItem(wardrobeItem: WardrobeItem, which: String) {
        if (which.equals("top")) {
            topItemList.add(wardrobeItem)
            _topItemListData.value = topItemList.toList()
        } else if (which.equals("bottom")) {
            bottomItemList.add(wardrobeItem)
            _bottomItemsListData.value = bottomItemList.toList()
        } else if (which.equals("set")) {
            setItemList.add(wardrobeItem)
            _setItemListData.value = setItemList
        }
    }


    fun deleteAllWardrobeItem(which: String) {
        if (which.equals("top")) {
            topItemList.clear()
            _topItemListData.value = topItemList.toList()
        } else if (which.equals("bottom")) {
            bottomItemList.clear()
            _bottomItemsListData.value = bottomItemList.toList()
        } else if (which.equals("set")) {
            setItemList.clear()
            _setItemListData.value = setItemList.toList()
        }
    }

    fun deleteWardrobeItem(pos: Int) {
        wardrobeItemList.removeAt(pos)
        _wardrobeItemListData.value = wardrobeItemList
    }


    /* 커뮤니티 이미지 */

    fun addCommunityItem(wardrobeItem: WardrobeItem) {
        communityItemList.add(wardrobeItem)
        _communityItemListData.value = wardrobeItemList
    }

}