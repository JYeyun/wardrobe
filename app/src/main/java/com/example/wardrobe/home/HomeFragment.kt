package com.example.wardrobe.home

import androidx.fragment.app.viewModels
import com.example.wardrobe.R
import com.example.wardrobe.base.BaseFragment
import com.example.wardrobe.databinding.FragmentHomeBinding
import com.example.wardrobe.home.model.HomeItem
import com.example.wardrobe.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    private val homeWeatherAdapter by lazy { HomeAdapter() { pos -> itemClickEvent(pos) } }

    private val homeCommunityAdapter by lazy { HomeAdapter() { pos -> itemClickEvent(pos) } }

    override fun initView() {
        initDummyData()

        bind {
            weatherAdapter = homeWeatherAdapter
            communityAdapter = homeCommunityAdapter
        }

        observeData()
    }

    private fun observeData() {
        viewModel.weatherItemListData.observe(viewLifecycleOwner){
            homeWeatherAdapter.submitList(it)
        }

        viewModel.communityItemListData.observe(viewLifecycleOwner){
            homeCommunityAdapter.submitList(it)
        }
    }

    private fun itemClickEvent(pos: Int) {
        // 아이템 클릭 시 이동하고 싶으면 이 함수에 넣으세요
        shortShowToast("아이템 클릭: $pos")
    }

    private fun initDummyData() {
        val dummyImageList = listOf(
            HomeItem(R.drawable.test_top),
            HomeItem(R.drawable.test_bottom)
        )

        viewModel.initCommunityDummyData(dummyImageList)
        viewModel.initWeatherDummyData(dummyImageList)
    }
}


