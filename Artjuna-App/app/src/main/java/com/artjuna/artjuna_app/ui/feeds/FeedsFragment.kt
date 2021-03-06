package com.artjuna.artjuna_app.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.FragmentFeedsBinding
import com.artjuna.artjuna_app.ui.adapter.PostAdapter
import com.artjuna.artjuna_app.utils.AppUtils
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
        getListPost()
        setButtonLikeClicked()
    }

    private fun setButtonLikeClicked() {
        postAdapter.onLikeClick = {post ->
            if(post.isLiked){
                addPostLiked(post)
            }else{
                deletePostLiked(post.id)
            }
        }
    }

    private fun deletePostLiked(id: String) {
        feedsViewModel.deletePostLikedById(id)
    }

    private fun addPostLiked(post: Post) {
        feedsViewModel.insertPostLiked(post)
        feedsViewModel.likePostById(post.id)
    }

    private fun setupAdapter() {
        binding.rvPost.adapter = postAdapter
    }

    private fun getListPost(){
        feedsViewModel.getPost().observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> {
                    showLoading(true)
                    showEmpty(false)
                }
                is Result.Error -> {
                    AppUtils.showToast(requireContext(), it.error)
                    showLoading(true)
                    showEmpty(false)
                }
                is Result.Success -> {
                    checkPostLiked(it.data)
                    showLoading(false)
                    showEmpty(it.data.isEmpty(), "There are no post yet")
                }
            }
        }
    }

    private fun checkPostLiked(data: List<Post>) {
        feedsViewModel.getAllPostLikedId().observe(viewLifecycleOwner){
            val listId = it
            data.map { it.isLiked = listId.contains(it.id) }
            showListToAdapter(data)
        }
    }

    private fun showListToAdapter(data:List<Post>) {
        postAdapter.submitList(data)
    }

    private fun showLoading(isLoading:Boolean){
        with(binding){
            postLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
            rvPost.visibility = if(isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showEmpty(empty: Boolean, message:String="") {
        binding.empty.root.visibility = if(empty) View.VISIBLE else View.GONE
        binding.empty.tvMessage.text = message
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}