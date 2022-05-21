package com.artjuna.artjuna_app.ui.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentPostBinding
import com.artjuna.artjuna_app.ui.adapter.PostAdapter
import com.artjuna.artjuna_app.ui.store.StoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment : Fragment() {


    private val storeViewModel:StoreViewModel by viewModel()
    private lateinit var binding: FragmentPostBinding
    private val postAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setData()
    }
    private fun setupAdapter(){
        binding.rvPost.adapter = postAdapter
    }
    private fun setData(){
        storeViewModel.getPost().observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> postAdapter.submitList(it.data)
            }
        }
    }



}