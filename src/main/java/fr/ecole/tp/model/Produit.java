package fr.ecole.tp.model;

public class Produit {
    private final String id;
    private final String nom;
    private final String categorie;
    private final double prix;
    private final boolean promotion;

    public Produit(String id, String nom, String categorie, double prix, boolean promotion) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.prix = prix;
        this.promotion = promotion;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public double getPrix() {
        return this.prix;
    }

    public boolean isPromotion() {
        return this.promotion;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", prix=" + prix +
                ", promotion=" + promotion +
                '}';
    }
}
