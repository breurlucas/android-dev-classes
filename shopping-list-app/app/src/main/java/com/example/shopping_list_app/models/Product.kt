package com.example.shopping_list_app.models

data class Product (
    var id: String? = null, // String is the standard format for id's in Firebase
    var name: String,
    var purchased: Boolean = false
)