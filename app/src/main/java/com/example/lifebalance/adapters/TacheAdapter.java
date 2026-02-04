package com.example.lifebalance.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lifebalance.R;
import com.example.lifebalance.activities.EditTacheActivity;
import com.example.lifebalance.database.TacheDAO;
import com.example.lifebalance.models.Tache;

import java.util.ArrayList;

public class TacheAdapter extends RecyclerView.Adapter<TacheAdapter.TacheViewHolder> {

    private ArrayList<Tache> liste;
    private Context context;

    public TacheAdapter(ArrayList<Tache> liste) {
        this.liste = liste;
    }

    public TacheAdapter(ArrayList<Tache> liste, Context context) {
        this.liste = liste;
        this.context = context;
    }

    @Override
    public TacheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tache, parent, false);
        return new TacheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TacheViewHolder holder, int position) {
        Tache tache = liste.get(position);

        holder.tvTitre.setText(tache.getTitre());
        holder.tvDescription.setText(tache.getDescription());
        holder.tvDate.setText(tache.getDate());

        // Clic pour modifier
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context != null) {
                    Intent intent = new Intent(context, EditTacheActivity.class);
                    intent.putExtra("id", tache.getId());
                    intent.putExtra("titre", tache.getTitre());
                    intent.putExtra("description", tache.getDescription());
                    intent.putExtra("date", tache.getDate());
                    context.startActivity(intent);
                }
            }
        });

        // Clic long pour supprimer
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (context != null) {
                    TacheDAO dao = new TacheDAO(context);
                    dao.deleteTache(tache.getId());
                    liste.remove(position);
                    notifyItemRemoved(position);
                    return true;
                }
                return false;
            }
        });
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
        TextView tvTitre, tvDescription, tvDate;

        TacheViewHolder(View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}