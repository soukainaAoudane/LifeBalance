package com.example.lifebalance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifebalance.R;
import com.example.lifebalance.adapters.TacheAdapter;
import com.example.lifebalance.database.TacheDAO;
import com.example.lifebalance.models.Tache;

import java.util.ArrayList;

public class ListeTachesActivity extends AppCompatActivity
        implements TacheAdapter.OnTacheChangeListener {

    private RecyclerView recyclerView;
    private TacheAdapter adapter;
    private TacheDAO tacheDAO;
    private TextView tvEmpty;
    private Button btnAjouter, btnRetour;
    private ArrayList<Tache> tachesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_taches);

        // Initialisation
        tacheDAO = new TacheDAO(this);

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnAjouter = findViewById(R.id.btnAjouter);
        btnRetour = findViewById(R.id.btnRetour);

        // Configuration RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TacheAdapter(tachesList, this);
        adapter.setOnTacheChangeListener(this);
        recyclerView.setAdapter(adapter);

        // Boutons
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListeTachesActivity.this, AddTacheActivity.class));
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void chargerTaches() {
        tachesList.clear();
        tachesList.addAll(tacheDAO.getAllTaches());
        adapter.notifyDataSetChanged();

        if (tachesList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTacheDeleted() {
        // Rafraîchir l'affichage si la liste devient vide
        chargerTaches();
    }

    @Override
    public void onTacheUpdated() {
        // Rafraîchir la liste
        chargerTaches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste quand on revient sur l'écran
        chargerTaches();
    }
}
