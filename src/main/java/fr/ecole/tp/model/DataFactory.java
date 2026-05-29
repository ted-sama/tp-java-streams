package fr.ecole.tp.model;

import java.util.List;

public class DataFactory {
    private DataFactory() {}

    public static List<Produit> createProduits() {
        return List.of(
                new Produit("P1", "Clavier", "Informatique", 45.0, false),
                new Produit("P2", "Souris", "Informatique", 15.0, false),
                new Produit("P3", "Ballon", "Sport", 10.0, true),
                new Produit("P4", "Air Jordan 1 Low", "Vêtements", 120.0, false),
                new Produit("P5", "Audi RS6", "Voiture", 60000.0, false),
                new Produit("P6", "Vélo", "Sport", 150.0, false),
                new Produit("P7", "Casque Bose QC45", "Musique", 150.0, true),
                new Produit("P8", "MacBook Air M5", "Informatique", 1200.0, false),
                new Produit("P9", "iPhone 17 Air", "Informatique", 1000.0, true),
                new Produit("P10", "Sac à dos Eastpak", "Voyage", 30.0, true),
                new Produit("P11", "Tante Quechua", "Voyage", 115.0, false),
                new Produit("P12", "Baton de randonnée", "Sport", 120.0, false)
        );
    }

    public static List<Client> createClients() {
        return List.of(
                new Client("C1", "Riyad MAHREZ", "riyadmahrez@gmail.com"),
                new Client("C2", "Victor WEMBANYAMA", "vic.wemby@gmail.com"),
                new Client("C3", "Hideo KOJIMA", "kojima@gmail.com"),
                new Client("C4", "François CORNET", "francois.cornet@gmail.com")
        );
    }

    public static List<Commande> createCommandes() {
        List<Produit> produits = createProduits();
        List<Client> clients = createClients();

        Commande cmd1 = new Commande("CMD1", clients.get(0), List.of(
                new LigneCommande(produits.get(0), 1),   // Clavier x1
                new LigneCommande(produits.get(1), 2)    // Souris x2
        ));

        Commande cmd2 = new Commande("CMD2", clients.get(1), List.of(
                new LigneCommande(produits.get(7), 1),   // MacBook Air M5 x1
                new LigneCommande(produits.get(8), 1)    // iPhone 17 Air x1
        ));

        Commande cmd3 = new Commande("CMD3", clients.get(2), List.of(
                new LigneCommande(produits.get(2), 3),   // Ballon x3
                new LigneCommande(produits.get(5), 1),   // Vélo x1
                new LigneCommande(produits.get(11), 2)   // Baton de randonnée x2
        ));

        Commande cmd4 = new Commande("CMD4", clients.get(0), List.of(
                new LigneCommande(produits.get(6), 1)    // Casque Bose QC45 x1
        ));

        Commande cmd5 = new Commande("CMD5", clients.get(3), List.of(
                new LigneCommande(produits.get(9), 1),   // Sac à dos Eastpak x1
                new LigneCommande(produits.get(10), 1)   // Tante Quechua x1
        ));

        return List.of(cmd1, cmd2, cmd3, cmd4, cmd5);
    }
}
