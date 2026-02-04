package com.example.lifebalance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lifebalance.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table Tache
        String CREATE_TABLE_TACHE =
                "CREATE TABLE Tache (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "titre TEXT NOT NULL," +
                        "description TEXT," +
                        "date TEXT," +
                        "statut TEXT" +
                        ")";
        db.execSQL(CREATE_TABLE_TACHE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Tache");
        onCreate(db);
    }
}