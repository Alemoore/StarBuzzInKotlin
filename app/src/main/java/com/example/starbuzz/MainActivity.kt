package com.example.starbuzz

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var favoriteCursor: Cursor
    private lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = list_options
        setupFavoriteListView()
        listView.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                val intent = Intent(this, DrinkCategoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupFavoriteListView(){
        val favoriteListView: ListView = list_favorites
        val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
        try {
            db = starbuzzDatabaseHelper.readableDatabase
            favoriteCursor = db.query("DRINK", arrayOf("_id", "NAME"),"FAVORITE = ?",
                arrayOf(1.toString()), null, null, null)
            val cursorAdapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, favoriteCursor, arrayOf("NAME"), intArrayOf(android.R.id.text1), 0)
            favoriteListView.adapter = cursorAdapter
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }
        favoriteListView.setOnItemClickListener { adapterView, view, i, id ->
            val intent = Intent(this, DrinkActivity::class.java)
            intent.putExtra(DrinkActivity.DRINKID, (id.toInt()))
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
       val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
       db = starbuzzDatabaseHelper.readableDatabase
       val listFavorites: ListView = findViewById(R.id.list_favorites)
       val newCursor = db.query("DRINK", arrayOf("_id", "NAME"), "FAVORITE = ?", arrayOf(1.toString()), null, null, null)
       val adapter = listFavorites.adapter as SimpleCursorAdapter
       adapter.changeCursor(newCursor)
       favoriteCursor = newCursor
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteCursor.close()
        db.close()
    }
}
