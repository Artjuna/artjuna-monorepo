package com.artjuna.artjuna_app.ui.mystore.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.databinding.FragmentMyPostBinding
import com.artjuna.artjuna_app.ui.mystore.activity.addpost.AddPostActivity

class MyPostFragment : Fragment() {


    private lateinit var binding: FragmentMyPostBinding

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
        populateView()
        getUserId()
    }

    private fun getUserId() {
        val userId = arguments?.getString(USER_ID)
    }

    private fun populateView() {
        binding.btnAddPost.setOnClickListener {
            startActivity(Intent(requireContext(), AddPostActivity::class.java))
        }
    }

    companion object{
        const val USER_ID = "USER_ID"
    }

}