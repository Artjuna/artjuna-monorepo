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
import com.artjuna.artjuna_app.ui.home.adapter.RecomAdapter
import com.artjuna.artjuna_app.ui.productlist.ProductListActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModel()
    private val recomAdapter = RecomAdapter()

    private val recomList = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setButtonClick()
        setupAdapter()
        setData()
        return root
    }

    private fun setButtonClick() {
        with(binding){
            btnRecomSeeall.setOnClickListener {
                val intent = Intent(requireContext(), ProductListActivity::class.java)
                intent.putParcelableArrayListExtra(ProductListActivity.EXTRA_PRODUCT_LIST, recomList)
                intent.putExtra(ProductListActivity.EXTRA_PAGE_TITLE, "Recommendation")
                startActivity(intent)
            }
        }
    }

    private fun setupAdapter() {
        binding.rvRecom.adapter = recomAdapter
    }

    private fun setData() {
        homeViewModel.getRecommended().observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> {
                    recomList.addAll(it.data)
                    recomAdapter.submitList(it.data)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}