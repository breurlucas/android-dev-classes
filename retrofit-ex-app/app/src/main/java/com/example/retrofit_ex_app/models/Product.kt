package com.example.retrofit_ex_app.models

/* Parameters should have the same names as the ones in the JSON object for the API calls to work
without the need of naming conversion (extra step) */

data class Product (
    val idProduto: Int,
    val nomeProduto: String,
    val descProduto: String,
    val precProduto: Float,
    val descontoPromocao: Float,
    val idCategoria: Int,
    val qtdMinEstoque: Int,
    val ativoProduto: Boolean
)