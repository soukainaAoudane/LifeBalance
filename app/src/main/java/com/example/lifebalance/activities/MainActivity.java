package com.example.lifebalance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifebalance.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAddTache, btnListTaches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation
        btnAddTache = findViewById(R.id.btnAddTache);
        btnListTaches = findViewById(R.id.btnListTaches);

        // Configuration des clics
        btnAddTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTacheActivity.class));
            }
        });

        btnListTaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListeTachesActivity.class));
            }
        });
    }
}