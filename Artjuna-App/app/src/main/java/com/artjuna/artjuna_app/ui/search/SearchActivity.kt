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
        populateView()
    }

    private fun setupAdapter() {
        binding.rvProduct.adapter = productAdapter
    }

    private fun populateView() {
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
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    AppUtils.showToast(this, it.error)
                }
                is Result.Success -> {
                    showLoading(false)
                    showTextResult(keyword)
                    productAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun showTextResult(keyword: String) {
        with(binding.tvResult){
            visibility = View.VISIBLE
            text = "Showing result for $keyword"
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
            if(loading){
                load.visibility = View.VISIBLE
                rvProduct.visibility = View.GONE
            }else{
                load.visibility = View.GONE
                rvProduct.visibility = View.VISIBLE
            }
        }
    }

}