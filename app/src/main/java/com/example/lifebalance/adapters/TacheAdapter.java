package com.example.lifebalance.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifebalance.R;
import com.example.lifebalance.activities.EditTacheActivity;
import com.example.lifebalance.database.TacheDAO;
import com.example.lifebalance.models.Tache;

import java.util.ArrayList;

public class TacheAdapter extends RecyclerView.Adapter<TacheAdapter.TacheViewHolder> {

    private ArrayList<Tache> liste;
    private Context context;
    private TacheDAO tacheDAO;

    // Interface pour les callbacks
    public interface OnTacheChangeListener {
        void onTacheDeleted();
        void onTacheUpdated();
    }

    private OnTacheChangeListener listener;

    public void setOnTacheChangeListener(OnTacheChangeListener listener) {
        this.listener = listener;
    }

    public TacheAdapter(ArrayList<Tache> liste, Context context) {
        this.liste = liste;
        this.context = context;
        this.tacheDAO = new TacheDAO(context);
    }

    @NonNull
    @Override
    public TacheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tache, parent, false);
        return new TacheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TacheViewHolder holder, int position) {
        Tache tache = liste.get(position);

        // Mettre à jour les vues
        holder.tvTitre.setText(tache.getTitre());
        holder.tvDescription.setText(tache.getDescription());
        holder.tvDate.setText(tache.getDate());
        holder.tvStatut.setText(tache.getStatut());

        // Changer la couleur de l'indicateur selon le statut
        holder.indicatorStatut.setBackgroundTintList(ColorStateList.valueOf(tache.getCouleurStatut()));

        // Bouton Modifier
        holder.btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirModification(tache);
            }
        });

        // Bouton Supprimer
        holder.btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmerSuppression(tache, position);
            }
        });

        // Clic sur toute la carte pour voir les détails
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirModification(tache);
            }
        });
    }

    private void ouvrirModification(Tache tache) {
        Intent intent = new Intent(context, EditTacheActivity.class);
        intent.putExtra("ID_TACHE", tache.getId());
        intent.putExtra("TITRE", tache.getTitre());
        intent.putExtra("DESCRIPTION", tache.getDescription());
        intent.putExtra("DATE", tache.getDate());
        intent.putExtra("STATUT", tache.getStatut());
        context.startActivity(intent);
    }

    private void confirmerSuppression(Tache tache, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous vraiment supprimer \"" + tache.getTitre() + "\" ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    supprimerTache(tache, position);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void supprimerTache(Tache tache, int position) {
        // Supprimer de la base de données
        tacheDAO.deleteTache(tache.getId());

        // Supprimer de la liste locale
        liste.remove(position);
        notifyItemRemoved(position);

        // Notifier les changements
        if (listener != null) {
            listener.onTacheDeleted();
        }

        Toast.makeText(context, "Tâche supprimée", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    public void updateList(ArrayList<Tache> newList) {
        this.liste = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    static class TacheViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View indicatorStatut;
        TextView tvTitre, tvDescription, tvDate, tvStatut;
        Button btnModifier, btnSupprimer;

        TacheViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            indicatorStatut = itemView.findViewById(R.id.indicatorStatut);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatut = itemView.findViewById(R.id.tvStatut);
            btnModifier = itemView.findViewById(R.id.btnModifier);
            btnSupprimer = itemView.findViewById(R.id.btnSupprimer);
        }
    }
}
