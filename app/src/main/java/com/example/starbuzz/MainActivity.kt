package com.example.starbuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = list_options
        listView.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                val intent = Intent(this, DrinkCategoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
