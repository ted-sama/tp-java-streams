package fr.ecole.tp;

import fr.ecole.tp.model.Commande;
import fr.ecole.tp.model.Produit;
import fr.ecole.tp.service.CommandeService;
import fr.ecole.tp.service.ParallelStreamService;
import fr.ecole.tp.service.ProduitService;

import java.util.List;
import java.util.Scanner;

public class Console {
    private List<Produit> produits;
    private List<Commande> commandes;
    private Scanner scanner = new Scanner(System.in);
    private ProduitService ps = new ProduitService();
    private CommandeService cs = new CommandeService();
    private ParallelStreamService plls =  new ParallelStreamService();

    public Console(List<Produit> produits, List<Commande> commandes) {
        this.produits = produits;
        this.commandes = commandes;
    }

    public void run() {
        // Fonctionnalité 16 : démonstration de génération d'un identifiant de commande
        cs.afficherNouveauId();

        while (true) {
            System.out.println("=== CATALOGUE CONSOLE ===");
            System.out.println("1. Afficher tous les produits\n" +
                    "2. Afficher les produits triés par prix croissant\n" +
                    "3. Afficher les produits d'une catégorie\n" +
                    "4. Rechercher les produits en promotion\n" +
                    "5. Afficher les noms des produits en majuscules\n" +
                    "6. Afficher les 3 produits les plus chers\n" +
                    "7. Calculer le prix total du catalogue\n" +
                    "8. Afficher les catégories uniques\n" +
                    "9. Afficher les commandes avec leurs produits\n" +
                    "10. Calculer le total de chaque commande\n" +
                    "11. Vérifier si tous les produits ont un prix positif\n" +
                    "12. Démonstration parallelStream\n" +
                    "0. Quitter");

            int choice = readChoice();

            switch (choice) {
                case 1: ps.afficherAvecPrefixe(produits);
                break;
                case 2: ps.afficherPrixCroissant(produits);
                break;
                case 3:
                    System.out.println("De quelle catégorie voulez-vous afficher les produits ?");
                    // Fonctionnalité 17 : nettoyage de la saisie (trim + minuscules)
                    String category = cs.nettoyer(scanner.nextLine());
                    ps.afficherParCategorie(produits, category);
                break;
                case 4: ps.afficherPromotions(produits);
                break;
                case 5: ps.afficherMajuscules(produits);
                break;
                case 6: ps.afficher3PlusCher(produits);
                break;
                case 7: System.out.println("Le prix total est " + ps.prixTotalSum(produits));
                break;
                case 8: ps.afficherCategoriesUniquesDistinct(produits);
                break;
                case 9: cs.afficherProduits(commandes);
                break;
                case 10: cs.afficherTotalParCommande(commandes);
                break;
                case 11:
                    if (ps.tousPrixPositifs(produits)) {
                        System.out.println("Tous les produits possèdent un prix positif");
                    } else {
                        System.out.println("Certains produits n'ont pas de prix positif");
                    }
                break;
                case 12: plls.demo(10000000);
                break;
                case 0:
                    System.out.println("Au revoir !");
                    return;
                default: break;
            }
        }
    }

    private int readChoice() {
        while (true) {
            System.out.println("\nVeuillez choisir une fonctionnalité :");
            String line = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 0 && choice <= 12) {
                    return choice;
                }
                System.out.println("Choix hors menu, entrez un nombre entre 0 et 12.");
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez saisir un nombre.");
            }
        }
    }
}
