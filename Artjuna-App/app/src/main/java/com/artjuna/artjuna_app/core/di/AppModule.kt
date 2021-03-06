package com.artjuna.artjuna_app.core.di

import com.artjuna.artjuna_app.ui.mystore.activity.addpost.AddPostViewModel
import com.artjuna.artjuna_app.ui.mystore.activity.addproduct.AddProductViewModel
import com.artjuna.artjuna_app.ui.address.AddressViewModel
import com.artjuna.artjuna_app.ui.auth.AuthViewModel
import com.artjuna.artjuna_app.ui.cart.CartlViewModel
import com.artjuna.artjuna_app.ui.checkout.CheckoutViewModel
import com.artjuna.artjuna_app.ui.customize.CustomizeViewModel
import com.artjuna.artjuna_app.ui.detailproduct.DetailViewModel
import com.artjuna.artjuna_app.ui.feeds.FeedsViewModel
import com.artjuna.artjuna_app.ui.home.HomeViewModel
import com.artjuna.artjuna_app.ui.likedpost.LikedPostViewModel
import com.artjuna.artjuna_app.ui.mystore.MyStoreViewModel
import com.artjuna.artjuna_app.ui.mystore.activity.orderhistory.OrderHistoryViewModel
import com.artjuna.artjuna_app.ui.productlist.ProductListViewModel
import com.artjuna.artjuna_app.ui.profile.ProfileViewModel
import com.artjuna.artjuna_app.ui.order.OrderViewModel
import com.artjuna.artjuna_app.ui.profilesettings.ProfileSettingsViewModel
import com.artjuna.artjuna_app.ui.search.SearchViewModel
import com.artjuna.artjuna_app.ui.splash.SplashViewModel
import com.artjuna.artjuna_app.ui.store.StoreViewModel
import com.artjuna.artjuna_app.ui.storefollowed.StoreFollowedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FeedsViewModel(get()) }
    viewModel { StoreViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { CheckoutViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { ProfileSettingsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { AddPostViewModel(get()) }
    viewModel { AddProductViewModel(get()) }
    viewModel { ProductListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { CartlViewModel(get()) }
    viewModel { LikedPostViewModel(get()) }
    viewModel { StoreFollowedViewModel(get()) }
    viewModel { MyStoreViewModel(get()) }
    viewModel { OrderViewModel(get()) }
    viewModel { OrderHistoryViewModel(get()) }
    viewModel { CustomizeViewModel(get())}
}
