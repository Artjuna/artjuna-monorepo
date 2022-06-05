package com.artjuna.artjuna_app.ui.productlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getCategory
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityProductListBinding
import com.artjuna.artjuna_app.ui.adapter.ProductAdapter
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProductListBinding
    private val viewModel:ProductListViewModel by viewModel()

    private val productAdapter = ProductAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        getType()
        setButtonClick()
    }

    private fun setAdapter() {
        binding.rvProduct.adapter = productAdapter
    }

    private fun getType() {
        if(intent.extras != null){
            val type = this.intent.extras?.getString(EXTRA_PAGE_TYPE)
            if(type == Constant.Recommendation){
                getRecommendation()
            }
            if(type == Constant.Category){
                getCategory()
            }
        }
    }

    private  fun getCategory(){
        val category = intent.extras?.getString(EXTRA_CATEGORY)
        binding.tvTitle.text = category
        viewModel.getProductByCategory(category!!).observe(this){
            when(it){
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    productAdapter.submitList(it.data)
                    showLoading(false)
                }
            }
        }
    }

    private fun getRecommendation() {
        binding.tvTitle.text = Constant.Recommendation
        viewModel.getRecommended().observe(this){
            when(it){
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    productAdapter.submitList(it.data)
                    showLoading(false)
                }
                is Result.Error -> {
                    AppUtils.showToast(this, it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun setButtonClick() {
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun showLoading(loading:Boolean){
        with(binding){
            if(loading){
                load.visibility = View.VISIBLE
                rvProduct.visibility = View.GONE
            }else{
                load.visibility = View.GONE
                rvProduct.visibility = View.VISIBLE
            }
        }
    }
    companion object{
        const val EXTRA_PAGE_TYPE = "EXTRA_PAGE_TYPE"
        const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
    }
}