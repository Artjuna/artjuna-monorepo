package com.artjuna.artjuna_app.utils

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


}