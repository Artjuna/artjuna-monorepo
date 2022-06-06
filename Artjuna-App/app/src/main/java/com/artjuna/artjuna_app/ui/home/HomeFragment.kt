package com.artjuna.artjuna_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentHomeBinding
import com.artjuna.artjuna_app.ui.adapter.ProductAdapter
import com.artjuna.artjuna_app.ui.cart.CartActivity
import com.artjuna.artjuna_app.ui.home.adapter.CatAdapter
import com.artjuna.artjuna_app.ui.productlist.ProductListActivity
import com.artjuna.artjuna_app.ui.search.SearchActivity
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModel()
    private val productAdapter = ProductAdapter()
    private val catAdapter = CatAdapter()

    private val categoriesList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClick()
        setupAdapter()
        getRecommendedData()
        getCategoriesData()
    }

    private fun setButtonClick() {
        with(binding){
            header.btnCart.setOnClickListener { startActivity(Intent(requireContext(), CartActivity::class.java)) }
            header.btnSearch.setOnClickListener { startActivity(Intent(requireContext(), SearchActivity::class.java)) }
            recom.btnSeeAll.setOnClickListener {
                val intent = Intent(requireContext(), ProductListActivity::class.java)
                intent.putExtra(ProductListActivity.EXTRA_PAGE_TYPE, Constant.Recommendation)
                startActivity(intent)
            }
        }
    }

    private fun setupAdapter() {
        binding.recom.rvRecom.adapter = productAdapter
        binding.cat.rvCategories.adapter = catAdapter
    }

    private fun getCategoriesData() {
        homeViewModel.getCategories().observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> {
                    showLoadingCat(false)
                    categoriesList.addAll(it.data)
                    catAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    showLoadingCat(false)
                    AppUtils.showToast(requireContext(), it.error)
                }
                is Result.Loading -> showLoadingCat(true)
            }
        }
    }


    private fun getRecommendedData() {
        homeViewModel.getRecommended(10).observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> {
                    showLoadingRecom(false)
                    productAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    showLoadingRecom(false)
                    AppUtils.showToast(requireContext(), it.error)
                }
                is Result.Loading -> showLoadingRecom(true)
            }
        }
    }

    private fun showLoadingRecom(isLoading:Boolean){
        with(binding){
            recomLoad.visibility = if(isLoading) View.VISIBLE else View.GONE
            recom.root.visibility = if(isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showLoadingCat(isLoading:Boolean){
        with(binding){
            catLoad.visibility = if(isLoading) View.VISIBLE else View.GONE
            cat.root.visibility = if(isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}