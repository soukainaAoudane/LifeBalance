package com.example.lifebalance.models;

public class Tache {
    private int id;
    private String titre;
    private String description;
    private String date;
    private String statut;
    private int priorite; // 1: Basse, 2: Moyenne, 3: Haute
    private String categorie;
    private boolean estImportant;

    // Constructeurs
    public Tache() {
        this.statut = "À faire";
        this.priorite = 2;
        this.categorie = "Général";
        this.estImportant = false;
    }

    public Tache(String titre, String description, String date, String statut) {
        this();
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.statut = statut;
    }

    public Tache(String titre, String description, String date, String statut,
                 int priorite, String categorie, boolean estImportant) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.statut = statut;
        this.priorite = priorite;
        this.categorie = categorie;
        this.estImportant = estImportant;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStatut() { return statut; }
    public int getPriorite() { return priorite; }
    public String getCategorie() { return categorie; }
    public boolean isEstImportant() { return estImportant; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setStatut(String statut) { this.statut = statut; }
    public void setPriorite(int priorite) { this.priorite = priorite; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setEstImportant(boolean estImportant) { this.estImportant = estImportant; }

    // Méthodes utilitaires
    public String getIconePriorite() {
        switch (priorite) {
            case 1: return "⬇";
            case 3: return "⬆";
            default: return "➡";
        }
    }

    public int getCouleurStatut() {
        switch (statut.toLowerCase()) {
            case "terminé": return 0xFF4CAF50;
            case "en cours": return 0xFFFF9800;
            default: return 0xFF6C63FF;
        }
    }
}