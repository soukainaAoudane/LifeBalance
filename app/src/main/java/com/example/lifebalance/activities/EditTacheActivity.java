package com.example.lifebalance.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifebalance.R;
import com.example.lifebalance.database.TacheDAO;
import com.example.lifebalance.models.Tache;

public class EditTacheActivity extends AppCompatActivity {

    private EditText etTitre, etDescription, etDate;
    private Button btnModifier, btnSupprimer;
    private TacheDAO tacheDAO;
    private int tacheId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tache);

        tacheDAO = new TacheDAO(this);

        // Récupérer l'ID de la tâche
        tacheId = getIntent().getIntExtra("id", -1);

        // Initialisation des vues
        etTitre = findViewById(R.id.etTitre);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        btnModifier = findViewById(R.id.btnModifier);
        btnSupprimer = findViewById(R.id.btnSupprimer);

        // Charger les données de la tâche
        chargerTache();

        // Configuration des boutons
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierTache();
            }
        });

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerTache();
            }
        });
    }

    private void chargerTache() {
        if (tacheId != -1) {
            // Pour simplifier, on récupère depuis les extras
            etTitre.setText(getIntent().getStringExtra("titre"));
            etDescription.setText(getIntent().getStringExtra("description"));
            etDate.setText(getIntent().getStringExtra("date"));
        }
    }

    private void modifierTache() {
        String titre = etTitre.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();

        if (titre.isEmpty()) {
            Toast.makeText(this, "Le titre est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        Tache tache = new Tache(titre, description, date, "À faire");
        tache.setId(tacheId);

        tacheDAO.updateTache(tache);

        Toast.makeText(this, "Tâche modifiée avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void supprimerTache() {
        tacheDAO.deleteTache(tacheId);

        Toast.makeText(this, "Tâche supprimée avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }
}