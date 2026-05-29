package fr.ecole.tp.service;

import fr.ecole.tp.model.Commande;
import fr.ecole.tp.model.LigneCommande;
import fr.ecole.tp.model.Produit;

import java.util.List;
import java.util.function.BiFunction;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandeService {

    // Fonctionnalité 13
    public void afficherProduits(List<Commande> commandes) {
        commandes.stream()
                .flatMap(c -> c.getLignes().stream())
                .map(LigneCommande::getProduit)
                .forEach(System.out::println);
    }
    // Fonctionnalité 14
    BiFunction<Produit, Integer, Double> calculLigne =
            (produit, quantite) -> produit.getPrix() * quantite;

    public void afficherTotalParCommande(List<Commande> commandes) {
        commandes.forEach(commande -> {
            double total = commande.getLignes().stream()
                    .mapToDouble(l -> calculLigne.apply(l.getProduit(), l.getQuantite()))
                    .sum();
            System.out.println("Commande " + commande.getId() + " : " + total + " €");
        });
    }

    // Fonctionnalité 15
    public Map<String, Double> totalParCommande(List<Commande> commandes) {
        return commandes.stream()
                .collect(Collectors.toMap(
                        Commande::getId,
                        commande -> commande.getLignes().stream()
                                .mapToDouble(l -> calculLigne.apply(l.getProduit(), l.getQuantite()))
                                .sum()
                ));
    }

        // Fonctionnalité 16
    Supplier<String> idGenerator = () -> UUID.randomUUID().toString();

    public void afficherNouveauId() {
        System.out.println("Nouvel ID de commande : " + idGenerator.get());
    }

    // Fonctionnalité 17
    UnaryOperator<String> nettoyerSaisie = s -> s.trim().toLowerCase();

    public String nettoyer(String saisie) {
        return nettoyerSaisie.apply(saisie);
    }

    public void afficherSaisieNettoyee(String saisie) {
        System.out.println("Saisie nettoyée : " + nettoyerSaisie.apply(saisie));
    }


}
