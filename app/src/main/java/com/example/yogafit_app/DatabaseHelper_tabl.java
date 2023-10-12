package com.example.yogafit_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.IOException;

public class DatabaseHelper_tabl extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "barbie_yogas.db";
    private static final int DATABASE_VERSION = 7;

    SQLiteDatabase db;

    public DatabaseHelper_tabl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void openDb() {
        try {
            db = getReadableDatabase();
        } catch (SQLiteException e) {
            throw e;
        }
    }

    public void closeDb() {
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    public Cursor getExs(String tableName) {
        String query = "SELECT * FROM " + tableName;
        return db.rawQuery(query, null);
    }
}