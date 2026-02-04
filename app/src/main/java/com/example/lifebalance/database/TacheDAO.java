package com.example.lifebalance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lifebalance.models.Tache;

import java.util.ArrayList;

public class TacheDAO {

    private SQLiteDatabase db;

    public TacheDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // CREATE
    public void insertTache(Tache tache) {
        ContentValues values = new ContentValues();
        values.put("titre", tache.getTitre());
        values.put("description", tache.getDescription());
        values.put("date", tache.getDate());
        values.put("statut", tache.getStatut());

        db.insert("Tache", null, values);
    }

    // READ ALL
    public ArrayList<Tache> getAllTaches() {
        ArrayList<Tache> liste = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Tache", null);

        while (cursor.moveToNext()) {
            Tache tache = new Tache(
                    cursor.getString(1), // titre
                    cursor.getString(2), // description
                    cursor.getString(3), // date
                    cursor.getString(4)  // statut
            );
            tache.setId(cursor.getInt(0));
            liste.add(tache);
        }
        cursor.close();
        return liste;
    }

    // UPDATE
    public void updateTache(Tache tache) {
        ContentValues values = new ContentValues();
        values.put("titre", tache.getTitre());
        values.put("description", tache.getDescription());
        values.put("date", tache.getDate());
        values.put("statut", tache.getStatut());

        db.update(
                "Tache",
                values,
                "id = ?",
                new String[]{String.valueOf(tache.getId())}
        );
    }

    // DELETE
    public void deleteTache(int id) {
        db.delete(
                "Tache",
                "id = ?",
                new String[]{String.valueOf(id)}
        );
    }
}