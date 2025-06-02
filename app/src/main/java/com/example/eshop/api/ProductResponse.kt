package com.example.eshop.api

import com.example.eshop.data.Product

data class ProductResponse(
    val products: List<Product>,
    val nextPageUrl: String?
)