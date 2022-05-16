package com.artjuna.artjuna_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artjuna.artjuna_app.databinding.FragmentHomeBinding
import com.artjuna.artjuna_app.ui.home.adapter.BestAdapter
import com.artjuna.artjuna_app.ui.home.adapter.CatAdapter
import com.artjuna.artjuna_app.ui.home.adapter.RecomAdapter
import com.artjuna.artjuna_app.utils.DummyData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val recomAdapter = RecomAdapter()
    private val catAdapter = CatAdapter()
    private val bestAdapter = BestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAdapter()
        setData()

        return root
    }

    private fun setupAdapter() {
        binding.rvRecom.adapter = recomAdapter
        binding.rvCat.adapter = catAdapter
        binding.rvBest.adapter = bestAdapter
    }

    private fun setData() {
        recomAdapter.submitList(DummyData.listProduct())
        catAdapter.submitList(DummyData.listCategory())
        bestAdapter.submitList(DummyData.listProduct())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}