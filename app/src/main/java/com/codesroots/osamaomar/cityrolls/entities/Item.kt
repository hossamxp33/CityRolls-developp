package com.codesroots.osamaomar.cityrolls.entities

data class Item(
    val created: String,
    val description: String,
    val favourites: List<Any>,
    val id: Int,
    val modified: String,
    val name: String,
    val photo: String,
    val price: Int,
    val subcategory_id: Int,
    val tax_id: Int,
    val total_rating: List<Any>
)