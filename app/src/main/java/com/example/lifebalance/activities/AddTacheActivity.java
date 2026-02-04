package com.example.lifebalance.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifebalance.R;
import com.example.lifebalance.database.TacheDAO;
import com.example.lifebalance.models.Tache;

public class AddTacheActivity extends AppCompatActivity {

    private EditText etTitre, etDescription, etDate;
    private Spinner spinnerStatut;
    private Button btnAjouter;
    private TacheDAO tacheDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tache);

        tacheDAO = new TacheDAO(this);

        // Initialisation des vues
        etTitre = findViewById(R.id.etTitre);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        spinnerStatut = findViewById(R.id.spinnerStatut);
        btnAjouter = findViewById(R.id.btnAjouter);

        // Configuration du spinner de statut
        setupSpinner();

        // Configuration du bouton
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterTache();
            }
        });
    }

    private void setupSpinner() {
        // Options de statut
        String[] statuts = {"À faire", "En cours", "Terminé"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statuts
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatut.setAdapter(adapter);
    }

    private void ajouterTache() {
        String titre = etTitre.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String statut = spinnerStatut.getSelectedItem().toString();

        // Validation
        if (titre.isEmpty()) {
            Toast.makeText(this, "Le titre est obligatoire", Toast.LENGTH_SHORT).show();
            etTitre.requestFocus();
            return;
        }

        // Si la date est vide, mettre "Aujourd'hui"
        if (date.isEmpty()) {
            date = "Aujourd'hui";
        }

        // Création de la tâche
        Tache tache = new Tache(titre, description, date, statut);

        // Insertion dans la base
        tacheDAO.insertTache(tache);

        // Feedback
        Toast.makeText(this, "Tâche ajoutée avec succès", Toast.LENGTH_SHORT).show();

        // Retour à l'écran principal
        finish();
    }
}