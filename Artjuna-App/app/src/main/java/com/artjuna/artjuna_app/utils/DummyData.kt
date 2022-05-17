package com.artjuna.artjuna_app.utils

import com.artjuna.artjuna_app.core.data.source.model.Category
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.model.Product

object DummyData {

    fun listProduct():List<Product>{
        val listProduct = ArrayList<Product>()
        for (i in 1..10){
            listProduct.add(
                Product(
                    id = "$i",
                    name = "Talenan Kayu Bagus Banget",
                    image = "https://images.unsplash.com/photo-1624811533744-f85d5325d49c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Y3V0dGluZyUyMGJvYXJkfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
                    price = 80000,
                    city = "Kota Bandung",
                    rating = 4.6,
                    sold = 231
                )
            )
        }
        return listProduct
    }

    fun listCategory():List<Category>{
        val listCategory = ArrayList<Category>()
        for (i in 1..10){
            listCategory.add(
                Category(
                    id = "$i",
                    name = "Lukisan",
                    image = "https://images.unsplash.com/photo-1549289524-06cf8837ace5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"
                )
            )
        }
        return listCategory
    }

    fun listPost():List<Post>{
        val listPost = ArrayList<Post>()
        for (i in 1..10){
            listPost.add(
                Post(
                    id = "$i",
                    userName = "erenmikasa13",
                    userImage = "https://static.wikia.nocookie.net/shingekinokyojin/images/a/a1/Eren_Jaeger_%28Anime%29_character_image.png/revision/latest?cb=20220123225500",
                    image = "https://cdn.shopify.com/s/files/1/0408/1097/1288/products/3_aa98c089-3883-4256-b04d-ad73580d8f29_1500x.jpg?v=1646050859",
                    productName = "Patung Eren Gak Pake Baju",
                    productId = "${i+20}",
                    like = 843,
                    caption = "Special: [Deluxe Version] Additional standing version Eren, additional base and exchangeable lower body sculpture. Note: [Special Version - limited to 88 units] ONLY for customers who purchased the previous release figures from Typical Scene Studio"
                )
            )
        }
        return listPost
    }





}