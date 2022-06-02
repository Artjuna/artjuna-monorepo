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
import com.artjuna.artjuna_app.ui.mystore.MyStoreActivity
import com.artjuna.artjuna_app.ui.order.OrderActivity
import com.artjuna.artjuna_app.ui.profilesettings.ProfileSettingsActivity
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

        showMenu(true)
        setButtonClick()
        getData()

    }

    private fun getData() {
        user = viewModel.getUser()
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
                val intent = Intent(requireContext(), ProfileSettingsActivity::class.java)
                intent.putExtra(ProfileSettingsActivity.EXTRA_USER, user)
                startActivity(intent)

            }
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

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}