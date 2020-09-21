package com.example.starbuzz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "starbuzz"
const val DB_VERSION = 2


class StarbuzzDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        updateMyDatabase(db, 0, DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        updateMyDatabase(db, oldVersion, newVersion)
    }

    private fun insertDrink(
        db: SQLiteDatabase,
        name: String,
        description: String,
        resourceId: Int
    ) {
        val drinkValues = ContentValues()
        drinkValues.put("NAME", name)
        drinkValues.put("DESCRIPTION", description)
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId)
        db.insert("DRINK", null, drinkValues)
    }

    private fun updateMyDatabase(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion <1) {
            db!!.execSQL(
                "CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME TEXT, "
                        + "DESCRIPTION TEXT, "
                        + "IMAGE_RESOURCE_ID INTEGER); "
            )
            insertDrink(
                db, "Cappuccino",
                "Espresso coffee topped with frothed hot milk or cream and often flavored with cinnamon",
                R.drawable.cappuccino
            )
            insertDrink(
                db, "Latte",
                "Espresso mixed with hot or steamed milk",
                R.drawable.latte
            )
            insertDrink(
                db, "Hot Chocolate",
                "Hot milk that is chocolate-flavored",
                R.drawable.chocolate
            )
        }
        if (oldVersion < 2) {
            db!!.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC")
        }
    }
}