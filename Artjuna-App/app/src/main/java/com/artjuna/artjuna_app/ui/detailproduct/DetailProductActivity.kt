package com.artjuna.artjuna_app.ui.detailproduct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityDetailProductBinding
import com.artjuna.artjuna_app.ui.checkout.CheckoutActivity
import com.artjuna.artjuna_app.ui.customize.CustomizeActivity
import com.artjuna.artjuna_app.ui.store.StoreActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailProductBinding
    private val viewModel:DetailViewModel by viewModel()
    private var product = Product()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getProductData()
        setButtonClick()
    }

    private fun addHasSen() {
        viewModel.addHasSeen(product.id).observe(this){
            Log.d("Detail", "Has Seen Added")
        }
    }

    private fun setButtonClick() {
        with(binding){
            store.btnVisitstore.setOnClickListener {
                val intent = Intent(this@DetailProductActivity, StoreActivity::class.java)
                intent.putExtra(StoreActivity.EXTRA_STORE_ID, product.storeId)
                intent.putExtra(StoreActivity.EXTRA_STORE_CITY, product.storeCity)
                startActivity(intent)
            }

            bottomBar.btnCheckout.setOnClickListener {
                val intent = Intent(this@DetailProductActivity, CheckoutActivity::class.java)
                intent.putExtra(CheckoutActivity.EXTRA_PRODUCT, product)
                startActivity(intent)
            }

            bottomBar.btnCustom.setOnClickListener {
                val intent = Intent(this@DetailProductActivity, CustomizeActivity::class.java)
                intent.putExtra(CustomizeActivity.EXTRA_PRODUCT, product)
                startActivity(intent)
            }
        }
    }

    private fun getProductData() {
        val extras = intent.extras
        if(extras!=null){
            product = extras.getParcelable<Product>(EXTRA_PRODUCT)!!
            populateView()
            addHasSen()
        }
    }

    private fun populateView() {
        with(binding){
            overview.btnBack.setOnClickListener { onBackPressed() }
            overview.ivImage.loadImage(product.image)
            overview.tvProductName.text = product.name
            overview.tvProductPrice.text = "Rp ${product.price}"
            overview.tvProductDetail.text = product.detail

            store.tvStoreName.text = product.storeId
            store.tvStoreCity.text = product.storeCity
        }
    }

    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }
}