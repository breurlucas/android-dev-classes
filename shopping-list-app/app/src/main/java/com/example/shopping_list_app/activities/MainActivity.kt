package com.example.shopping_list_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shopping_list_app.R
import com.example.shopping_list_app.models.Product
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_item.*
import kotlinx.android.synthetic.main.card_item.view.*

class MainActivity : AppCompatActivity() {

    var database: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(getCurrentUser() == null) {
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

            // Open standard Firebase authentication screen
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), 0
            )
        }
        else {
            configureDatabase() // CONFIG DATABASE
            Toast.makeText(this, "Authenticated", Toast.LENGTH_LONG).show()
        }

        fab.setOnClickListener {
            newItem()
        }
    }

    fun newItem() {

        val editText = EditText(this) // Instantiating a view element programmatically
        editText.hint = "Name"

        AlertDialog.Builder(this)
            .setTitle("New item")
            /* We can only add a single view through the builder
            (Though it can be a layout containing multiple views) */
            .setView(editText)
            // Kotlin recommended way of writing a closure for the last param (arrow function)
            // This closure replaces the 'null' entry for the listener param
            .setPositiveButton("Insert") { dialog, button ->
                val prod = Product(name = editText.text.toString())

                /* We create a new node 'products' under the user node. The 'newProduct' item is
                going to be listed under that node */
                val newProduct = database?.child("products")?.push()

                prod.id = newProduct?.key

                newProduct?.setValue(prod)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    fun refreshUI(products: List<Product>) {
        container.removeAllViews()

        products.forEach {
            val item = layoutInflater.inflate(R.layout.card_item, container, false)
            item.cbPurchased.isChecked = it.purchased
            item.txtName.text = it.name

            container.addView(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                configureDatabase() // CONFIG DATABASE
                Toast.makeText(this, "Authenticated", Toast.LENGTH_LONG).show()
            }
            else {
                finishAffinity()
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }

    fun configureDatabase() {
        val user = getCurrentUser()

        /* Kotlin won't let us treat 'user' as non nullable; however, we made sure it cannot happen
        in the method 'onActivityResult' by finishing the app if the user won't authenticate.
        The '?.let' isn't really necessary but we don't wan't to use '!!'*/
        user?.let{

            /* This way we reference the root node. However, we would like to reference a user node
            instead */

//            database = FirebaseDatabase.getInstance().reference

            // Referencing the user node (child node). If there is none, Firebase creates it
            database = FirebaseDatabase.getInstance().reference.child(user.uid)
        }
    }
}