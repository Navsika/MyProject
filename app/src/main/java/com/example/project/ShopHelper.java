package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ShopHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shop.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "store";
    public static final String PRODUCT = "product";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String POINT = "point";
    public static final String HOW_MUCH = "howMuch";
    private Context context;

    private SQLiteDatabase db;


    public ShopHelper(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + "(_id integer PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                PRICE + " INTEGER, " +
                POINT + " TEXT, " +
                HOW_MUCH + " INTEGER);";
        sqLiteDatabase.execSQL(sqlQuery);




        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
}

