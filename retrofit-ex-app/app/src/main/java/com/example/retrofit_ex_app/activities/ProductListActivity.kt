package com.example.retrofit_ex_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofit_ex_app.R
import com.example.retrofit_ex_app.models.Product
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.product_card.view.*
import java.text.NumberFormat

/* Ideally we should use a recycle view instead of the dynamically loaded cards as we are
implementing in this app; the recycle view element provides better performance.

However, for the purpose of this exercise, a first implementation of 'Retrofit', the dynamically
loaded cards serves us well. */

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
    }

    fun updateScreen(products: List<Product>?) {

        /* We clear and load all cards each time we update the screen. With a large number of
        entries this can lead to poor performance of the application. This issue is solved by the
        recycle view. */
        container.removeAllViews()

        val formatter = NumberFormat.getCurrencyInstance()

        products?.let {
            for(product in it) { // For each (Java)
                val card = layoutInflater.inflate(R.layout.product_card, container, false)

                card.txtName.text = product.nomeProduto
                card.txtPrice.text = formatter.format(product.precProduto)

                container.addView(card)
            }
        }
    }
}