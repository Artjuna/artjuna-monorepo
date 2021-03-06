package com.artjuna.artjuna_app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.databinding.FragmentProfileBinding
import com.artjuna.artjuna_app.ui.cart.CartActivity
import com.artjuna.artjuna_app.ui.likedpost.LikedPostActivity
import com.artjuna.artjuna_app.ui.mystore.MyStoreActivity
import com.artjuna.artjuna_app.ui.order.OrderActivity
import com.artjuna.artjuna_app.ui.profilesettings.ProfileSettingsActivity
import com.artjuna.artjuna_app.ui.storefollowed.StoreFollowedActivity
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel:ProfileViewModel by viewModel()
    private var user = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClick()
        getUserData()

    }

    private fun getUserData() {
        user = viewModel.getUser()
        showMenu(user.isStore)
        populateUserView()
    }

    private fun populateUserView() {
        with(binding){
            tvFullname.text = user.fullName
            tvUsername.text = user.userName
        }
    }

    private fun setButtonClick() {
        with(binding){
            btnSetting.setOnClickListener {
                moveToSetting()
            }
            btnMycart.setOnClickListener {
                startActivity(Intent(requireContext(),CartActivity::class.java))
            }
            btnMyorder.setOnClickListener {
                startActivity(Intent(requireContext(),OrderActivity::class.java))
            }
            btnLikedpost.setOnClickListener {
                startActivity(Intent(requireContext(), LikedPostActivity::class.java))
            }
            btnStorefollow.setOnClickListener {
                startActivity(Intent(requireContext(), StoreFollowedActivity::class.java))
            }
            btnMystore.setOnClickListener {
                if(numberAndLocationEmpty()){
                    AppUtils.showToast(requireContext(), "You have to fill your phone number and city first")
                    moveToSetting()
                }else{
                    moveToMyStore()
                }
            }
        }
    }

    private fun numberAndLocationEmpty():Boolean{
        return user.numberWA.isEmpty() || user.city.isEmpty() || user.province.isEmpty()
    }

    private fun moveToMyStore() {
        val intent = Intent(requireContext(), MyStoreActivity::class.java)
        intent.putExtra(MyStoreActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun moveToSetting(){
        val intent = Intent(requireContext(), ProfileSettingsActivity::class.java)
        intent.putExtra(ProfileSettingsActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun showMenu(isStore:Boolean){
        if(isStore){
            binding.btnMystore.visibility = View.VISIBLE
        }else{
            binding.btnMystore.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        getUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}