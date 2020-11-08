package com.example.retrofit_ex_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofit_ex_app.R
import com.example.retrofit_ex_app.models.Product
import kotlinx.android.synthetic.main.activity_product_list.*

/* Ideally we should use a recycle view instead of the dynamically loaded cards as we are
implementing in this app; the recycle view element provides better performance.

However, for the purpose of this exercise, a first implementation of 'Retrofit', the dynamically
loaded cards serves us well. */

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
    }

    fun updateScreen(products: List<Product>) {

        for(product in products) { // For each (Java)
            val card = layoutInflater.inflate(R.layout.product_card, container, false)
        }
    }
}