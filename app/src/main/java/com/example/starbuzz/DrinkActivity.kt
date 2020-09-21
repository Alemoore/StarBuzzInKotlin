package com.example.starbuzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drink.*

class DrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)
        val position = intent.extras!!.get(DRINKID) as Int
        val drink = DrinkManager.drinks[position]
        photo.setImageResource(drink.image)
        drink_name.text = drink.name
        drink_description.text = drink.description
    }

    companion object {
        const val DRINKID = "DrinkId"
    }
}