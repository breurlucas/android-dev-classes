package com.example.retrofit_ex_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.retrofit_ex_app.R
import com.example.retrofit_ex_app.apis.ProductAPI
import com.example.retrofit_ex_app.models.Product
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.product_card.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    override fun onResume() {
        super.onResume()
        getAllProducts()
    }

    fun getAllProducts() {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://oficinacordova.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(ProductAPI::class.java)

        val call = api.getAll()

        val callback = object: Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful) {
                    updateScreen(response.body())
                }
                else {
                    Toast.makeText(this@ProductListActivity,
                            "Connection error", Toast.LENGTH_LONG).show()
                    // Check issue in the API response itself
                    Log.e("API ERROR", response.errorBody().toString())
                }

            }

            // Check issue in calling the API/getting a response from it
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@ProductListActivity,
                        "Connection error", Toast.LENGTH_LONG).show()
                Log.e("ProductListActivity", "getAllProducts", t) // tag (Activity), msg (Method), t
            }

        }

        call.enqueue(callback)
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