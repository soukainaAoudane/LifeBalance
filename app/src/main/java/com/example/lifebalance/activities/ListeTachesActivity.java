package com.example.lifebalance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifebalance.R;
import com.example.lifebalance.adapters.TacheAdapter;
import com.example.lifebalance.database.TacheDAO;

public class ListeTachesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TacheAdapter adapter;
    private TacheDAO tacheDAO;
    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_taches);

        // Initialisation
        tacheDAO = new TacheDAO(this);

        // Configuration RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter
        adapter = new TacheAdapter(tacheDAO.getAllTaches());
        recyclerView.setAdapter(adapter);

        // Bouton retour
        btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste quand on revient sur l'écran
        adapter.updateList(tacheDAO.getAllTaches());
    }
}