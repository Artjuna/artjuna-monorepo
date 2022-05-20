package com.artjuna.artjuna_app.ui.productlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityProductListBinding
import com.artjuna.artjuna_app.ui.productlist.adapter.ProductAdapter

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProductListBinding
    private val productAdapter = ProductAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setData()
        populateView()
    }

    private fun populateView() {
        if(intent.extras != null){
            val title = this.intent.extras?.getString(EXTRA_PAGE_TITLE)
            binding.tvTitle.text = title
        }
    }

    private fun setData() {
        if(intent.extras != null){
            val productList = this.intent.extras?.getParcelableArrayList<Product>(EXTRA_PRODUCT_LIST)
            productAdapter.submitList(productList)
        }
    }

    private fun setAdapter() {
        binding.rvProduct.adapter = productAdapter
    }

    companion object{
        const val EXTRA_PAGE_TITLE = "EXTRA_PAGE_TITLE"
        const val EXTRA_PRODUCT_LIST = "EXTRA_PRODUCT_LIST"
    }
}