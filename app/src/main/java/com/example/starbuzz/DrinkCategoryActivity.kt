package com.example.starbuzz

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_drink_category.*

class DrinkCategoryActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    private lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_category)
        try {
            val starbuzzDatabaseHelper: SQLiteOpenHelper = StarbuzzDatabaseHelper(this)
            db = starbuzzDatabaseHelper.readableDatabase
            cursor = db.query("DRINK", arrayOf("_id", "Name"), null, null, null, null, null)
            val listAdapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, arrayOf("NAME"), intArrayOf(android.R.id.text1), 0)
            drinks_list.adapter = listAdapter
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }

        drinks_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DrinkActivity::class.java)
            intent.putExtra(DrinkActivity.DRINKID, position)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor.close()
        db.close()
    }
}