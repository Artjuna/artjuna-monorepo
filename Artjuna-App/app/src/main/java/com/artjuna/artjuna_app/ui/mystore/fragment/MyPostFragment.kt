package com.artjuna.artjuna_app.ui.mystore.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentMyPostBinding
import com.artjuna.artjuna_app.ui.adapter.PostAdapter
import com.artjuna.artjuna_app.ui.mystore.MyStoreViewModel
import com.artjuna.artjuna_app.ui.mystore.activity.addpost.AddPostActivity
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPostFragment : Fragment() {


    private lateinit var binding: FragmentMyPostBinding
    private val viewModel:MyStoreViewModel by viewModel()
    private val postAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        populateView()
        getUserId()
    }

    private fun setupAdapter() {
        binding.rvPost.adapter = postAdapter
    }

    private fun getUserId() {
        val userId = arguments?.getString(USER_ID)
        getListPost(userId!!)
    }

    private fun getListPost(userId: String) {
        viewModel.getPostByUserId(userId).observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> {
                    showLoading(true)
                    showEmpty(false)
                }
                is Result.Error -> {
                    AppUtils.showToast(requireContext(), it.error)
                    showLoading(false)
                    showEmpty(false)
                }
                is Result.Success -> {
                    postAdapter.submitList(it.data)
                    showLoading(false)
                    showEmpty(it.data.isEmpty(), "You haven't created any post yet")
                }
            }
        }
    }

    private fun showEmpty(empty: Boolean, message:String="") {
        binding.empty.root.visibility = if(empty) View.VISIBLE else View.GONE
        binding.empty.tvMessage.text = message
    }

    private fun populateView() {
        binding.btnAddPost.setOnClickListener {
            startActivity(Intent(requireContext(), AddPostActivity::class.java))
        }
    }

    private fun showLoading(isLoading:Boolean){
        with(binding){
            postLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
            rvPost.visibility = if(isLoading) View.GONE else View.VISIBLE
        }
    }

    companion object{
        const val USER_ID = "USER_ID"
    }

}