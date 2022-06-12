package com.artjuna.artjuna_app.ui.checkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Order
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.model.User
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivityCheckoutBinding
import com.artjuna.artjuna_app.ui.address.AddressActivity
import com.artjuna.artjuna_app.ui.loading.LoadingDialog
import com.artjuna.artjuna_app.utils.AppUtils
import com.artjuna.artjuna_app.utils.AppUtils.loadImage
import com.artjuna.artjuna_app.utils.AppUtils.sendOrderToWA
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel


class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCheckoutBinding
    private lateinit var loadingDialog: LoadingDialog

    private val viewModel:CheckoutViewModel by viewModel()
    private var product = Product()
    private var mAddress = Address()
    private var store = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoading()
        setButtonClick()
        getProduct()
        getAddress()
    }

    private fun setupLoading() {
        loadingDialog = LoadingDialog(this,false)
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            address.btnChangeAddress.setOnClickListener {
                startActivity(Intent(this@CheckoutActivity,AddressActivity::class.java))
            }
            bottomBar.btnOrder.setOnClickListener {
                if(mAddress.name.isEmpty() || mAddress.number.isEmpty() || mAddress.address.isEmpty()){
                    AppUtils.showToast(this@CheckoutActivity, "You have to fill your address")
                    address.btnChangeAddress.requestFocus()
                }else{
                    addOrder()
//                    AppUtils.sendOrderToWA(this@CheckoutActivity, "6285210938775", this@CheckoutActivity.product)
                }
            }
        }
    }

    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Your order has been created")
            .setCancelable(false)
            .setMessage("Contact seller for further information")
            .setPositiveButton("Contact Seller") { dialog, which ->
                contactSeller()
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun contactSeller() {
        AppUtils.sendOrderToWA(this@CheckoutActivity, store.numberWA, this@CheckoutActivity.product, mAddress)
    }

    private fun addOrder() {
        val order = collectOrderData()
        viewModel.addOrder(order).observe(this){
            when(it){
                is Result.Loading -> loadingDialog.show()
                is Result.Error -> {
                    loadingDialog.dismiss()
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    loadingDialog.dismiss()
                    showSuccessDialog()
                }
            }
        }
    }

    private fun collectOrderData(): Order {
        val order = Order(
            productId = product.id,
            storeId = product.storeId,
            price = product.price,
            buyerNumber = mAddress.number,
            buyerAddress = "${mAddress.address} ${mAddress.postalCode}"
        )
        return order
    }

    private fun getAddress(){
        mAddress = viewModel.getAddress()
        populateViewAddress(mAddress)
    }

    private fun populateViewAddress(address: Address) {
        with(binding.address){
            if(address.name == ""){
                tvNameNumber.text = "You haven't add your address"
                tvAddress.text = ""
            }else{
                tvNameNumber.text = "${address.name} (${address.number})"
                tvAddress.text = "${address.address} ID ${address.postalCode}"
            }
        }
    }

    private fun getProduct() {
        val extras = intent.extras
        if(extras!=null){
            val product = extras.getParcelable<Product>(EXTRA_PRODUCT)
            this.product = product!!
            getStoreData()
            populateViewProduct(product)
            populatePrice(product.price)
        }
    }

    private fun getStoreData() {
        viewModel.getStoreDataById(product.storeId).observe(this){
            when(it){
                is Result.Success -> store = it.data
            }
        }
    }

    private fun populatePrice(price: Int) {
        val mPrice = "Rp $price"
        binding.price.tvPrice.text = mPrice
        binding.bottomBar.tvPrice.text = mPrice
    }

    private fun populateViewProduct(product: Product) {
        with(binding.product){
            tvStoreName.text = product.storeName
            tvStoreCity.text = product.storeCity
            ivImage.loadImage(AppUtils.getProductImageURL(product.image))
            tvName.text = product.name
            tvPrice.text = "Rp ${product.price}"
        }
    }

    override fun onResume() {
        super.onResume()
        getAddress()
    }
    companion object{
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }
}