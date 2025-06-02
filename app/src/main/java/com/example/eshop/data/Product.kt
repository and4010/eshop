package com.example.eshop.data

data class Product(
    val id: Int,
    val name: String,
    val price: Price,
    val discountPrice : DiscountPrice,
    val image: String
)

data class Price(
    val value: Double,
    val currency: String
)

data class DiscountPrice(
    val value: Double,
    val currency: String
)