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

public class AddTacheActivity extends AppCompatActivity {

    private EditText etTitre, etDescription, etDate;
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
        btnAjouter = findViewById(R.id.btnAjouter);

        // Configuration du bouton
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterTache();
            }
        });
    }

    private void ajouterTache() {
        String titre = etTitre.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();

        // Validation
        if (titre.isEmpty()) {
            Toast.makeText(this, "Le titre est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création de la tâche
        Tache tache = new Tache(titre, description, date, "À faire");

        // Insertion dans la base
        tacheDAO.insertTache(tache);

        // Feedback
        Toast.makeText(this, "Tâche ajoutée avec succès", Toast.LENGTH_SHORT).show();

        // Retour à l'écran principal
        finish();
    }
}