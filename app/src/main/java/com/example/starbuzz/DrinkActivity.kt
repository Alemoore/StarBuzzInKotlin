package com.example.starbuzz

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_drink.*

class DrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)
        val position = intent.extras!!.get(DRINKID) as Int
        val starbuzzDatabaseHelper: SQLiteOpenHelper = StarbuzzDatabaseHelper(this)
        try {
            val db: SQLiteDatabase = starbuzzDatabaseHelper.readableDatabase
            val cursor = db.query("DRINK", arrayOf("NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"), "_id=?", arrayOf((position + 1).toString()), null, null, null)
            if (cursor.moveToFirst()) {
                val nameText = cursor.getString(0)
                val descriptionText = cursor.getString(1)
                val imageResourceId = cursor.getInt(2)
                val isFavorite: Boolean = cursor.getInt(3) == 1
                drink_name.text = nameText
                drink_description.text = descriptionText
                photo.setImageResource(imageResourceId)
                favorite.isChecked = isFavorite
                cursor.close()
                db.close()
            }
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickFavorite(view: View) {
        val drinkId: Int = intent.extras!!.getInt(DRINKID) + 1
        val contentValues = ContentValues()
        contentValues.put("FAVORITE", favorite.isChecked)
        val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
        try {
            val db = starbuzzDatabaseHelper.writableDatabase
            db.update("DRINK", contentValues, "_id=?", arrayOf(drinkId.toString()))
            db.close()
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val DRINKID = "DrinkId"
    }

    @Suppress("DEPRECATION")
    private inner class UpdateDrinkTask: AsyncTask<Int, Unit, Boolean>() {
        private lateinit var drinkValues: ContentValues

        override fun onPreExecute() {
            val favorite: CheckBox = findViewById(R.id.favorite)
            drinkValues = ContentValues()
            drinkValues.put("FAVORITE", favorite.isChecked)
        }

        override fun doInBackground(vararg p0: Int?): Boolean {
            val drinkId = p0[0]
            val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this@DrinkActivity)
            try {
                val db = starbuzzDatabaseHelper.writableDatabase
                db.update("DRINK", drinkValues, "_id = ?", arrayOf(drinkId.toString()))
                db.close()
                return true
            } catch (e: SQLiteException) {
                return false
            }
        }

        override fun onPostExecute(result: Boolean?) {
            if (!result!!) {
                Toast.makeText(this@DrinkActivity, "Database unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }
}