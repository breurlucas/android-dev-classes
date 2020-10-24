package com.example.pdm_p1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define array of items
        val items = arrayOf("Select an option:", "A", "B", "C", "D")

        // Create adapter and define item layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

        // Define dropdown layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Attribute adapter to the spinner
        spOptions.adapter = adapter

        // Listen to button click
        btnOptions.setOnClickListener {

            // Read the selected position in the spinner
            var itemPos = spOptions.selectedItemPosition

            // If the selected position is different than "0", show toast with the selected option
            if (itemPos != 0) {
                Toast.makeText(this,
                    "You have selected the option: " + spOptions.getItemAtPosition(itemPos).toString(),Toast.LENGTH_LONG).show()

            }
        }
    }
}