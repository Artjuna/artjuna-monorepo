package com.artjuna.artjuna_app.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artjuna.artjuna_app.databinding.FragmentFeedsBinding
import com.artjuna.artjuna_app.ui.feeds.adapter.PostAdapter
import com.artjuna.artjuna_app.utils.DummyData

class FeedsFragment : Fragment() {

    private var _binding: FragmentFeedsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val postAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val feedsViewModel =
            ViewModelProvider(this).get(FeedsViewModel::class.java)

        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAdapter()
        setData()

        return root
    }

    private fun setupAdapter() {
        binding.rvPost.adapter = postAdapter
    }

    private fun setData(){
        postAdapter.submitList(DummyData.listPost())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}