package fr.ecole.tp;

import fr.ecole.tp.model.Commande;
import fr.ecole.tp.model.DataFactory;
import fr.ecole.tp.model.Produit;
import fr.ecole.tp.service.CommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CommandeServiceTest {

    private CommandeService commandeService;
    private List<Commande> commandes;

    @BeforeEach
    void setUp() {
        commandeService = new CommandeService();
        commandes = DataFactory.createCommandes();
    }

    // Test 1 totalParCommande
    @Test
    void testCalculTotalCommandeCMD1() {
        Map<String, Double> totaux = commandeService.totalParCommande(commandes);
        assertEquals(75.0, totaux.get("CMD1"), 0.01);
    }

    // Test 2 totalParCommande
    @Test
    void testCalculTotalCommandeCMD2() {
        Map<String, Double> totaux = commandeService.totalParCommande(commandes);
        assertEquals(2200.0, totaux.get("CMD2"), 0.01);
    }

    // Test 3 totalParCommande
    @Test
    void testTotalParCommandeContientToutesLesCommandes() {
        Map<String, Double> totaux = commandeService.totalParCommande(commandes);
        assertEquals(5, totaux.size());
        assertTrue(totaux.containsKey("CMD1"));
        assertTrue(totaux.containsKey("CMD2"));
        assertTrue(totaux.containsKey("CMD3"));
        assertTrue(totaux.containsKey("CMD4"));
        assertTrue(totaux.containsKey("CMD5"));
    }

    // Test 4 totalParCommande
    @Test
    void testCalculTotalCommandeCMD3() {
        Map<String, Double> totaux = commandeService.totalParCommande(commandes);
        assertEquals(420.0, totaux.get("CMD3"), 0.01);
    }

    // Test 5 calculLigne
    @Test
    void testCalculLigne() {
        Produit produit = new Produit("P1", "Clavier", "Informatique", 45.0, false);
        double resultat = commandeService.calculLigne.apply(produit, 3);
        assertEquals(135.0, resultat, 0.01);
    }

    // Test 6 nettoyerSaisie
    @Test
    void testNettoyerSaisie() {
        String resultat = commandeService.nettoyerSaisie.apply(" BLABLA ");
        assertEquals("blabla", resultat);
    }

    // Test 7 idGenerator
    @Test
    void testIdGeneratorUnicite() {
        String id1 = commandeService.idGenerator.get();
        String id2 = commandeService.idGenerator.get();
        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);
    }

    // Test 8 totalParCommande
    @Test
    void testCalculTotalCommandeUnSeulProduit() {
        Map<String, Double> totaux = commandeService.totalParCommande(commandes);
        assertEquals(150.0, totaux.get("CMD4"), 0.01);
    }
}
