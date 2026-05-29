package fr.ecole.tp.service;


import fr.ecole.tp.model.Produit;
import fr.ecole.tp.util.ProduitFormatter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ProduitService {

    //Fonctionnalité 1
    public void afficherToutProduits(List<Produit> produits){
        produits.forEach(ProduitFormatter.afficherProduit);
    }

    // Fonctionnalité 2
    public void afficherToutFormates(List<Produit> produits){
        produits.stream()
                    .map(ProduitFormatter.formater)
                    .forEach(System.out::println);
    }

    //Fonctionnalité 3
    public Predicate<Produit> estEnPromotion = Produit::isPromotion;
    public Predicate<Produit> prixSuperieurA100 = p-> p.getPrix() > 100;
    public Predicate<Produit> categorieInformatique = p-> p.getCategorie().equals("Informatique");

    //Fonctionnalité 4
    public void AfficherFiltrePromoEtPrixSup(List<Produit> produits){
        produits.stream()
                .filter(estEnPromotion)
                .filter(prixSuperieurA100)
                .forEach(ProduitFormatter.afficherProduit);
    }

    //Fonctionnalité 5
    public void afficherMajuscules(List<Produit> produits){
        produits.stream()
                .map(Produit::getNom)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    //Fonctionnalité 6
    public void afficherPrixCroissant(List<Produit> produits){
        produits.stream()
                .sorted(Comparator.comparing(Produit::getPrix))
                .forEach(ProduitFormatter.afficherProduit);
    }

    public void afficherPrixDecroissant(List<Produit> produits){
        produits.stream()
                .sorted(Comparator.comparing(Produit::getPrix).reversed())
                .forEach(ProduitFormatter.afficherProduit);
    }

    public void afficherParCategoriePuisNom(List<Produit> produits){
        produits.stream()
                .sorted(Comparator.comparing(Produit::getCategorie).thenComparing(Produit::getNom))
                .forEach(ProduitFormatter.afficherProduit);
    }

    //Fonctionnalité 7
    public void afficherCategoriesUniquesDistinct(List<Produit> produits){
        produits.stream()
                .map(Produit::getCategorie)
                .distinct()
                .forEach(System.out::println);
    }

    public void afficherCategoriesUniquesToSet(List<Produit> produits){
        Set<String> categories = produits.stream()
                .map(Produit::getCategorie)
                .collect(Collectors.toSet());
        categories.forEach(System.out::println);
    }
    //Fonctionnalité 8
    public void afficher3PlusCher(List<Produit> produits){
        produits.stream()
                .sorted(Comparator.comparing(Produit::getPrix).reversed())
                .limit(3)
                .forEach(ProduitFormatter.afficherProduit);
    }

    //Fonctionnalité 9
    public void afficherPage(List<Produit> produits, int page){

        produits.stream()
                .map(ProduitFormatter.formater)
                .skip((page - 1) * 5L)
                .limit(5L)
                .forEach(System.out::println);
    }

    //Fonctionnalité 10
    public double prixTotalReduce(List<Produit> produits){
        return produits.stream()
                .map(Produit::getPrix)
                .reduce(0.0, (total, prix) -> total + prix);
    }

    //Fonctionnalité 11
    public double prixTotalSum(List<Produit> produits){
        return produits.stream()
                .mapToDouble(Produit::getPrix)
                .sum();
    }

    //Fonctionnalité 12
    public boolean existeProduitEnPromo(List<Produit> produits){
        return produits.stream()
                .anyMatch(estEnPromotion);
    }

    public boolean tousPrixPositifs(List<Produit> produits){
        return produits.stream()
                .allMatch(p -> p.getPrix() > 0);
    }

    public Optional<Produit> premierInformatique(List<Produit> produits){
        return produits.stream()
                .filter(categorieInformatique)
                .findFirst();
    }

    public Optional<Produit> produitLePlusCher(List<Produit> produits){
        return produits.stream()
                .max(Comparator.comparing(Produit::getPrix));
    }

}
