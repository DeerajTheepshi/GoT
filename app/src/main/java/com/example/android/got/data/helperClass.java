package com.example.android.got.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.got.data.contractClass.historytable;
import com.example.android.got.data.contractClass.searchTable;

public class helperClass extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Delta3";
    private static int DATABSE_VERSION = 1;

    public helperClass(Context context) {
        super(context, DATABASE_NAME,null,DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + historytable.TABLE_NAME + " ("
                + historytable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + historytable.C_NAME + " TEXT NOT NULL, "
                + historytable.C_SPOUSE + " TEXT, "
                + historytable.C_CUL + " TEXT, "
                + historytable.C_HOUSE+ " TEXT, "
                + historytable.C_IMAGE+ " TEXT, "
                + historytable.code+ " TEXT, "
                + historytable.C_TITLES + " TEXT ); ";

        String query2 = "CREATE TABLE " + searchTable.TABLE_NAME + " ("
                + searchTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + searchTable.NAME+ " TEXT UNIQUE, "
                + searchTable.DATA + " TEXT ); ";

        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
