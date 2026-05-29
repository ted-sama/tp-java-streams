package fr.ecole.tp.model;

import java.util.List;

public class Commande {
    private String id;
    private Client client;
    private List<LigneCommande> lignes;

    public Commande(String id, Client client, List<LigneCommande> lignes) {
        this.id = id;
        this.client = client;
        this.lignes = lignes;
    }

    public String getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id='" + id + '\'' +
                ", client=" + client +
                ", lignes=" + lignes +
                '}';
    }
}
