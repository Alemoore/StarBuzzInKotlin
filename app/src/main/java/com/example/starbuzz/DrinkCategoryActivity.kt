package com.example.starbuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_drink_category.*

class DrinkCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_category)
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, DrinkManager.drinks)
        drinks_list.adapter = arrayAdapter
        drinks_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DrinkActivity::class.java)
            intent.putExtra(DrinkActivity.DRINKID, position)
            startActivity(intent)
        }
    }
}