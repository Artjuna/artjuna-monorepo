package com.artjuna.artjuna_app.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.artjuna.artjuna_app.databinding.ActivitySearchBinding
import com.artjuna.artjuna_app.utils.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val searchViewModel:SearchViewModel by viewModel()
    private lateinit var binding:ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateView()
    }

    private fun populateView() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            etSearch.requestFocus()
            etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = textView.text.toString()
                    AppUtils.showToast(this@SearchActivity, keyword)
                    showTextResult(keyword)
                    hideKeyboard()
                    return@OnEditorActionListener true
                }
                false
            })
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

}