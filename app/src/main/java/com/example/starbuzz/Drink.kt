package com.example.starbuzz


data class Drink(val name: String, var description: String, val image: Int) {
    override fun toString(): String {
        return name
    }
}
