package com.example.wardrobe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wardrobe.home.model.HomeItem

class HomeViewModel: ViewModel() {

    private val weatherItemList = mutableListOf<HomeItem>()
    private val _weatherItemListData = MutableLiveData<List<HomeItem>>()
    val weatherItemListData: LiveData<List<HomeItem>> = _weatherItemListData

    private val communityItemList = mutableListOf<HomeItem>()
    private val _communityItemListData = MutableLiveData<List<HomeItem>>()
    val communityItemListData: LiveData<List<HomeItem>> = _communityItemListData

    /* 옷장 이미지 */
    fun addHomeWeatherItem(item: HomeItem){
        weatherItemList.add(item)
        _weatherItemListData.value = weatherItemList.toList()
    }
    /* 커뮤니티 이미지 */
    fun addHomeCommunityItem(item: HomeItem){
        communityItemList.add(item)
        _communityItemListData.value = communityItemList.toList()
    }

    fun initCommunityDummyData(itemList: List<HomeItem>) {
        itemList.forEach { item -> addHomeCommunityItem(item) }
    }

    fun initWeatherDummyData(itemList: List<HomeItem>) {
        itemList.forEach { item -> addHomeWeatherItem(item) }
    }

}