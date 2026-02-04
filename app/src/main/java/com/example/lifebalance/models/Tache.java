package com.example.lifebalance.models;

public class Tache {
    private int id;
    private String titre;
    private String description;
    private String date;
    private String statut; // "À faire", "En cours", "Terminé"

    // Constructeurs
    public Tache() {
        this.statut = "À faire";
    }

    public Tache(String titre, String description, String date, String statut) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.statut = statut;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStatut() { return statut; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setStatut(String statut) { this.statut = statut; }

    // Méthode pour obtenir la couleur selon le statut
    public int getCouleurStatut() {
        switch (statut) {
            case "Terminé":
                return 0xFF4CAF50; // Vert
            case "En cours":
                return 0xFFFF9800; // Orange
            case "À faire":
            default:
                return 0xFF6C63FF; // Violet
        }
    }
}