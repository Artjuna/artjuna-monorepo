package com.artjuna.artjuna_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentHomeBinding
import com.artjuna.artjuna_app.ui.home.adapter.CatAdapter
import com.artjuna.artjuna_app.ui.home.adapter.RecomAdapter
import com.artjuna.artjuna_app.ui.productlist.ProductListActivity
import com.artjuna.artjuna_app.ui.search.SearchActivity
import com.artjuna.artjuna_app.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModel()
    private val recomAdapter = RecomAdapter()
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
            header
            header.btnSearch.setOnClickListener { startActivity(Intent(requireContext(), SearchActivity::class.java)) }
            recom.btnSeeAll.setOnClickListener {
                val intent = Intent(requireContext(), ProductListActivity::class.java)
                intent.putExtra(ProductListActivity.EXTRA_PAGE_TITLE, Constant.Recommendation)
                startActivity(intent)
            }
        }
    }

    private fun setupAdapter() {
        binding.recom.rvRecom.adapter = recomAdapter
        binding.cat.rvCategories.adapter = catAdapter
    }

    private fun getCategoriesData() {
        homeViewModel.getCategories().observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> {
                    categoriesList.clear()
                    categoriesList.addAll(it.data)
                    catAdapter.submitList(it.data)
                }
            }
        }
    }


    private fun getRecommendedData() {
        homeViewModel.getRecommended(10).observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> {
                    showLoading(false)
                    recomAdapter.submitList(it.data)
                }
                is Result.Loading -> showLoading(true)
            }
        }
    }

    private fun showLoading(isLoading:Boolean){
        if(isLoading){
            with(binding){
                recomLoad.visibility = View.VISIBLE
                recom.root.visibility = View.GONE
            }
        } else{
            with(binding){
                recomLoad.visibility = View.GONE
                recom.root.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}