package com.artjuna.artjuna_app.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentFeedsBinding
import com.artjuna.artjuna_app.ui.adapter.PostAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedsFragment : Fragment() {

    private var _binding: FragmentFeedsBinding? = null
    private val feedsViewModel:FeedsViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val postAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setData()
    }

    private fun setupAdapter() {
        binding.rvPost.adapter = postAdapter
    }

    private fun setData(){
        feedsViewModel.getPost().observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> postAdapter.submitList(it.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}