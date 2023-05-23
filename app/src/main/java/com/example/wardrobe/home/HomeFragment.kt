package com.example.wardrobe.home

import androidx.fragment.app.viewModels
import com.example.wardrobe.R
import com.example.wardrobe.base.BaseFragment
import com.example.wardrobe.databinding.FragmentHomeBinding
import com.example.wardrobe.home.model.HomeItem
import com.example.wardrobe.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    private val homeWeatherAdapter by lazy { HomeAdapter() }

    private val homeCommunityAdapter by lazy { HomeAdapter() }

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

    private fun initDummyData() {
        val dummyImageList = listOf(
            HomeItem(R.drawable.test_top),
            HomeItem(R.drawable.test_bottom)
        )

        viewModel.initCommunityDummyData(dummyImageList)
        viewModel.initWeatherDummyData(dummyImageList)
    }
}


