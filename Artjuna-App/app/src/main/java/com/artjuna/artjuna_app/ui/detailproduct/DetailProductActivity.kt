package com.artjuna.artjuna_app.ui.detailproduct

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.databinding.ActivityDetailProductBinding
import com.artjuna.artjuna_app.ui.checkout.CheckoutActivity
import com.artjuna.artjuna_app.ui.store.StoreActivity
import com.artjuna.artjuna_app.utils.AppUtils.loadImage

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if(extras!=null){
            val product = extras.getParcelable<Product>(EXTRA_PRODUCT)
            populateView(product!!)
        }
    }

    private fun populateView(product: Product) {
        with(binding){
            overview.btnBack.setOnClickListener { onBackPressed() }
            overview.ivImage.loadImage(product.image)
            overview.tvProductName.text = product.name
            overview.tvProductPrice.text = "Rp ${product.price}"
            overview.tvProductDetail.text = product.detail

            store.ivStoreImage.loadImage(product.storeImage)
            store.tvStoreName.text = product.storeName
            store.tvStoreCity.text = product.storeCity
            store.btnVisitstore.setOnClickListener {
                startActivity(Intent(this@DetailProductActivity, StoreActivity::class.java))
            }

            bottomBar.btnCheckout.setOnClickListener {
                val intent = Intent(this@DetailProductActivity, CheckoutActivity::class.java)
                intent.putExtra(CheckoutActivity.EXTRA_PRODUCT, product)
                startActivity(intent)
            }
        }
    }

    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }
}