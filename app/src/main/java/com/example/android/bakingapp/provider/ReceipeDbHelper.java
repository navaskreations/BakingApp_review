package com.example.android.bakingapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.bakingapp.pojo.Receipe;

public class ReceipeDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "receipe.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public ReceipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold the plants data
        final String SQL_CREATE_RECEIPE_TABLE = "CREATE TABLE " + IngredientContract.ReceipeEntry.TABLE_NAME + " (" +
                IngredientContract.ReceipeEntry._ID + " INTEGER," +
                IngredientContract.ReceipeEntry.COLUMN_RECEIPE_ID + " INTEGER NOT NULL, " +
                IngredientContract.ReceipeEntry.COLUMN_RECEIPE_NAME + " VARCHAR NOT NULL, " +
                IngredientContract.ReceipeEntry.COLUMN_INGREDEINT + " VARCHAR NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_RECEIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientContract.ReceipeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
