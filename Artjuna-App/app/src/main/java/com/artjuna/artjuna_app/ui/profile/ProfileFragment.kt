package com.artjuna.artjuna_app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artjuna.artjuna_app.databinding.FragmentProfileBinding
import com.artjuna.artjuna_app.ui.cart.CartActivity
import com.artjuna.artjuna_app.ui.mystore.MyStoreActivity
import com.artjuna.artjuna_app.ui.order.OrderActivity
import com.artjuna.artjuna_app.utils.AppUtils

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMenu(true)
        setButtonClick()

    }

    private fun setButtonClick() {
        with(binding){
            btnMycart.setOnClickListener {
                startActivity(Intent(requireContext(),CartActivity::class.java))
            }
            btnMyorder.setOnClickListener {
                startActivity(Intent(requireContext(),OrderActivity::class.java))
            }
            btnLikedpost.setOnClickListener {
                AppUtils.showToast(requireContext(),"Coming Soon")
            }
            btnStorefollow.setOnClickListener {
                AppUtils.showToast(requireContext(),"Coming Soon")
            }
            btnMystore.setOnClickListener {
                startActivity(Intent(requireContext(),MyStoreActivity::class.java))
            }
        }
    }

    private fun showMenu(isStore:Boolean){
        if(isStore){
            binding.btnStorefollow.visibility = View.GONE
            binding.btnMystore.visibility = View.VISIBLE
        }else{
            binding.btnStorefollow.visibility = View.VISIBLE
            binding.btnMystore.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}