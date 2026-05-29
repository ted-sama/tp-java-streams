package fr.ecole.tp.service;

import fr.ecole.tp.model.Commande;
import fr.ecole.tp.model.LigneCommande;

import java.util.List;

public class CommandeService {

    // Fonctionnalité 13
    public void afficherProduits(List<Commande> commandes) {
        commandes.stream()
                .flatMap(c -> c.getLignes().stream())
                .map(LigneCommande::getProduit)
                .forEach(System.out::println);
    }
}
