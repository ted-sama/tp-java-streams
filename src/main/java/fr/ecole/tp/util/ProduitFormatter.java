package fr.ecole.tp.util;

import fr.ecole.tp.model.Produit;

import java.util.function.Consumer;
import java.util.function.Function;

public class ProduitFormatter {

    public static  Consumer<Produit> afficherProduit = System.out::println;

    public static Function<Produit, String> formater = p -> String.format("[%s] %s — %.2f €", p.getCategorie().toUpperCase(), p.getNom(), p.getPrix());

}
