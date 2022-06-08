package com.artjuna.artjuna_app.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.databinding.ActivitySearchBinding
import com.artjuna.artjuna_app.ui.adapter.ProductAdapter
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val viewModel:SearchViewModel by viewModel()
    private lateinit var binding:ActivitySearchBinding
    private val productAdapter = ProductAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        setButtonClick()
    }

    private fun setupAdapter() {
        binding.rvProduct.adapter = productAdapter
    }

    private fun setButtonClick() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            etSearch.requestFocus()
            etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = textView.text.toString()
                    getProductByKeyword(keyword)
                    hideKeyboard()
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    private fun getProductByKeyword(keyword: String) {
        viewModel.getProductByName(keyword).observe(this){
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
                    showLoading(false)
                    showEmpty(it.data.isEmpty(), "Product not found. Please try different keyword")
                    showTextResult(it.data.isNotEmpty(),keyword)
                    productAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun showEmpty(empty: Boolean, message:String="") {
        binding.empty.root.visibility = if(empty) View.VISIBLE else View.GONE
        binding.empty.tvMessage.text = message
    }

    private fun showTextResult(isNotEmpty:Boolean,keyword: String) {
        if(isNotEmpty){
            with(binding.tvResult){
                visibility = View.VISIBLE
                text = "Showing result for $keyword"
            }
        }
    }

    private fun hideKeyboard() {
        binding.etSearch.clearFocus()
        val `in`: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0)
    }

    private fun showLoading(loading:Boolean){
        with(binding){
            load.visibility = if(loading) View.VISIBLE else View.GONE
            rvProduct.visibility = if(loading) View.GONE else View.VISIBLE
        }
    }

}