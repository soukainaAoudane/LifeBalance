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

public class EditTacheActivity extends AppCompatActivity {

    private EditText etTitre, etDescription, etDate;
    private Spinner spinnerStatut;
    private Button btnModifier, btnSupprimer;
    private TacheDAO tacheDAO;
    private int tacheId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tache);

        tacheDAO = new TacheDAO(this);

        // Récupérer l'ID de la tâche
        tacheId = getIntent().getIntExtra("ID_TACHE", -1);

        // Initialisation des vues
        etTitre = findViewById(R.id.etTitre);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        spinnerStatut = findViewById(R.id.spinnerStatut);
        btnModifier = findViewById(R.id.btnModifier);
        btnSupprimer = findViewById(R.id.btnSupprimer);

        // Configurer le spinner de statut
        configurerSpinnerStatut();

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

    private void configurerSpinnerStatut() {
        // Options de statut
        String[] statuts = {"À faire", "En cours", "Terminé"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statuts
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatut.setAdapter(adapter);
    }

    private void chargerTache() {
        if (tacheId != -1) {
            etTitre.setText(getIntent().getStringExtra("TITRE"));
            etDescription.setText(getIntent().getStringExtra("DESCRIPTION"));
            etDate.setText(getIntent().getStringExtra("DATE"));

            // Sélectionner le bon statut dans le spinner
            String statut = getIntent().getStringExtra("STATUT");
            ArrayAdapter adapter = (ArrayAdapter) spinnerStatut.getAdapter();
            int position = adapter.getPosition(statut);
            if (position >= 0) {
                spinnerStatut.setSelection(position);
            }
        }
    }

    private void modifierTache() {
        String titre = etTitre.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();
        String statut = spinnerStatut.getSelectedItem().toString();

        if (titre.isEmpty()) {
            Toast.makeText(this, "Le titre est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        Tache tache = new Tache(titre, description, date, statut);
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