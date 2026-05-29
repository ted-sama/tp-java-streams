package fr.ecole.tp;

import fr.ecole.tp.model.Commande;
import fr.ecole.tp.model.DataFactory;
import fr.ecole.tp.model.Produit;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Produit> produits = DataFactory.createProduits();
        List<Commande> commandes = DataFactory.createCommandes();
        Console console = new Console(produits, commandes);

        console.run();
    }
}
