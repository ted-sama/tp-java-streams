package fr.ecole.tp;

import fr.ecole.tp.model.DataFactory;
import fr.ecole.tp.model.Produit;
import fr.ecole.tp.service.ProduitService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ProduitServiceTest {

    private ProduitService service;
    private List<Produit> produits;

    @BeforeEach
    void setUp() {
        service = new ProduitService();
        produits = DataFactory.createProduits();
    }

    @Test
    void testFiltrerLesProduitEnPromotion() {
        List<Produit> resultat = produits.stream()
                .filter(service.estEnPromotion)
                .toList();

        assertEquals(4, resultat.size());
        assertTrue(resultat.stream().allMatch(Produit::isPromotion));
    }

    @Test
    void testFiltrerLesProduitAvecPrixSuperieurA100() {
        List<Produit> resultat = produits.stream()
                .filter(service.prixSuperieurA100)
                .toList();

        assertEquals(8, resultat.size());
        assertTrue(resultat.stream().allMatch(p -> p.getPrix() > 100));
    }

    @Test
    void testTransformerNomEnMajuscules() {
        List<String> resultat = produits.stream()
                .map(Produit::getNom)
                .map(String::toUpperCase)
                .toList();

        assertTrue(resultat.stream().allMatch(nom -> nom.equals(nom.toUpperCase())));
        assertEquals(produits.size(), resultat.size());
    }

    @Test
    void testCalculTotalCatalogueAvecReduce() {
        double total = service.prixTotalReduce(produits);

        assertEquals(62955.0, total);
    }

    @Test
    void testCalculerTotalCatalogueAvecMapToDoubleSum() {
        double total = service.prixTotalSum(produits);

        assertEquals(62955.0, total);
    }

    @Test
    void testRecuperationCategoriesUniques() {
        List<String> resultat = produits.stream()
                .map(Produit::getCategorie)
                .distinct()
                .toList();

        assertEquals(6, resultat.size());
        assertTrue(resultat.containsAll(List.of("Informatique", "Sport", "Vêtements", "Voiture", "Musique", "Voyage")));
    }

    @Test
    void testProduitLePlusCher() {
        Optional<Produit> resultat = service.produitLePlusCher(produits);

        assertTrue(resultat.isPresent());
        assertEquals("Audi RS6", resultat.get().getNom());
        assertEquals(60000.0, resultat.get().getPrix());
    }

    @Test
    void testTousPrixPositifs() {
        assertTrue(service.tousPrixPositifs(produits));
    }

    @Test
    void testAfficherPage() {
        ByteArrayOutputStream sortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sortie));

        service.afficherPage(produits, 1);

        System.setOut(System.out);

        String resultat = sortie.toString();
        assertFalse(resultat.isEmpty());
        assertEquals(5, resultat.lines().count());
    }

    @Test
    void testAfficherPrixCroissant() {
        ByteArrayOutputStream sortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sortie));

        service.afficherPrixCroissant(produits);

        System.setOut(System.out);

        String resultat = sortie.toString();
        assertFalse(resultat.isEmpty());
        assertEquals(produits.size(), resultat.lines().count());
        assertTrue(resultat.lines().findFirst().orElse("").contains("Ballon"));
    }
}