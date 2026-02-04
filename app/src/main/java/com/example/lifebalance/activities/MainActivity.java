package com.example.lifebalance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.lifebalance.R;
import com.example.lifebalance.database.TacheDAO;
import com.example.lifebalance.models.Tache;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CardView cardAdd, cardView;
    private TextView tvStats;
    private TacheDAO tacheDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation
        tacheDAO = new TacheDAO(this);
        cardAdd = findViewById(R.id.cardAdd);
        cardView = findViewById(R.id.cardView);
        tvStats = findViewById(R.id.tvStats);

        // Configuration des clics
        cardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTacheActivity.class));
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListeTachesActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mettre à jour les statistiques à chaque retour sur l'écran
        updateStats();
    }

    private void updateStats() {
        ArrayList<Tache> taches = tacheDAO.getAllTaches();
        int total = taches.size();
        int termine = 0;
        for (Tache tache : taches) {
            if ("Terminé".equals(tache.getStatut())) {
                termine++;
            }
        }
        tvStats.setText(total + " tâches • " + termine + " terminées");
    }
}
