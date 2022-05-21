package com.artjuna.artjuna_app.ui.mystore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.databinding.FragmentMyProductBinding

class MyProductFragment : Fragment() {

    private lateinit var binding:FragmentMyProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyProductBinding.inflate(inflater, container, false)
        return binding.root
    }

}