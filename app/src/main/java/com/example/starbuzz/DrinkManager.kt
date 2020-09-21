package com.example.starbuzz

class DrinkManager() {
    companion object {
        val drinks = arrayOf(
            Drink(
                "Cappuccino",
                "Espresso coffee topped with frothed hot milk or cream and often flavored with cinnamon",
                R.drawable.cappuccino
            ),
            Drink(
                "Latte",
                "Espresso mixed with hot or steamed milk",
                R.drawable.latte
            ),
            Drink(
                "Hot Chocolate",
                "Hot milk that is chocolate-flavored",
                R.drawable.chocolate
            )
        )
    }
}