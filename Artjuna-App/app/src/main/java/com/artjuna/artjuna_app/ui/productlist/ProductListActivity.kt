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
                is Result.Loading -> {
                    showLoading(true)
                    showEmpty(false)
                }
                is Result.Error -> {
                    showLoading(false)
                    showEmpty(false)
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    productAdapter.submitList(it.data)
                    showEmpty(it.data.isEmpty(), "There are currently no products in this category")
                    showLoading(false)
                }
            }
        }
    }

    private fun showEmpty(empty: Boolean, message:String="") {
        binding.empty.root.visibility = if(empty) View.VISIBLE else View.GONE
        binding.empty.tvMessage.text = message
    }

    private fun getRecommendation() {
        binding.tvTitle.text = Constant.Recommendation
        viewModel.getRecommended().observe(this){
            when(it){
                is Result.Loading -> {
                    showLoading(true)
                    showEmpty(false)
                }
                is Result.Error -> {
                    AppUtils.showToast(this, it.error)
                    showLoading(false)
                    showEmpty(false)
                }
                is Result.Success -> {
                    productAdapter.submitList(it.data)
                    showLoading(false)
                    showEmpty(it.data.isEmpty(), "There are currently no products recommended for you")
                }
            }
        }
    }

    private fun setButtonClick() {
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun showLoading(loading:Boolean){
        with(binding){
            load.visibility = if(loading) View.VISIBLE else View.GONE
            rvProduct.visibility = if(loading) View.GONE else View.VISIBLE
        }
    }
    companion object{
        const val EXTRA_PAGE_TYPE = "EXTRA_PAGE_TYPE"
        const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
    }
}