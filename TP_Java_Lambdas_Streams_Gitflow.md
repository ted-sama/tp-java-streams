# TP Java — Lambdas, interfaces fonctionnelles et Streams

## Sujet

Vous travaillez en groupe de 3 sur une application console Java permettant de gérer un petit catalogue de produits et des commandes clients.

L’objectif du TP est de mettre en pratique les notions suivantes :

- expressions lambda ;
- interfaces fonctionnelles : `Predicate`, `Function`, `Consumer`, `Supplier`, `BiFunction`, `UnaryOperator` ;
- références de méthodes ;
- variables effectivement finales ;
- API Stream : `filter`, `map`, `flatMap`, `distinct`, `sorted`, `limit`, `skip` ;
- opérations terminales : `collect`, `toList`, `count`, `anyMatch`, `allMatch`, `findFirst`, `max`, `reduce` ;
- collecte vers `List`, `Set`, `Map` ;
- tests unitaires ;
- usage raisonné de `parallelStream()` ;
- Gitflow sur un dépôt public ;
- projet Maven ;
- programme Java en mode console.

Durée totale : **7 heures**.

---

# 1. Organisation du groupe

Chaque groupe est composé de 3 étudiants.

## Rôles conseillés

| Rôle | Responsabilités |
|---|---|
| Étudiant 1 — Référent modèle / données | Classes métier, données de test, génération de données |
| Étudiant 2 — Référent traitements Stream | Filtres, tris, transformations, statistiques |
| Étudiant 3 — Référent console / Git / tests | Menu console, Gitflow, tests unitaires, documentation |

Les rôles ne doivent pas empêcher l’entraide. Chaque étudiant doit faire au moins une branche `feature/*` et au moins une pull request.

---

# 2. Contraintes techniques obligatoires

## 2.1 Projet Maven

Le projet doit être un projet Maven standard.

Structure attendue :

```text
tp-java-streams/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── fr/ecole/tp/
│   │           ├── Main.java
│   │           ├── model/
│   │           ├── service/
│   │           └── fr.ecole.tp.util/
│   └── test/
│       └── java/
│           └── fr/ecole/tp/
```

Version Java attendue : **Java 17 minimum**.

Dépendance de test attendue : **JUnit 5**.

Exemple minimal de `pom.xml` :

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>fr.ecole</groupId>
    <artifactId>tp-java-streams</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.jupiter.version>5.10.2</junit.jupiter.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
        </plugins>
    </build>
</project>
```

Commandes Maven attendues :

```bash
mvn clean compile
mvn test
mvn package
```

---

# 3. Gitflow obligatoire

## 3.1 Principe rapide

Gitflow est une méthode d’organisation des branches Git.

Elle sépare :

- la branche `main`, qui contient uniquement une version stable ;
- la branche `develop`, qui contient le travail en cours validé ;
- les branches `feature/*`, utilisées pour développer une fonctionnalité isolée ;
- éventuellement les branches `release/*`, utilisées pour préparer une livraison ;
- éventuellement les branches `hotfix/*`, utilisées pour corriger rapidement un bug en production.

Dans ce TP, vous utiliserez surtout :

```text
main
develop
feature/...
```

## 3.2 Règles imposées

Le dépôt Git doit être **public** et le lien doit être envoyé à l'enseignant avant le 29/05/2026 à 17h00.

Branches obligatoires :

```text
main
develop
feature/modeles
feature/services-stream
feature/console
feature/tests
```

Minimum attendu :

- chaque étudiant crée au moins une branche `feature/*` ;
- chaque fonctionnalité est développée dans une branche dédiée ;
- chaque branche `feature/*` est fusionnée dans `develop` via pull request ;
- `main` ne doit recevoir que la version finale ;
- aucun développement direct dans `main` ;
- aucun développement direct dans `develop`, sauf correction mineure validée par le groupe.

## 3.3 Commandes Git indicatives

Initialisation :

```bash
git init
git branch -M main
git add .
git commit -m "Initialisation du projet Maven"
git remote add origin <url-du-repo-public>
git push -u origin main
```

Création de `develop` :

```bash
git checkout -b develop
git push -u origin develop
```

Création d’une feature :

```bash
git checkout develop
git pull
git checkout -b feature/services-stream
```

Fin d’une feature :

```bash
git add .
git commit -m "Ajout des traitements Stream"
git push -u origin feature/services-stream
```

Puis ouvrir une pull request vers `develop`.

Finalisation :

```bash
git checkout main
git merge develop
git tag v1.0.0
git push origin main --tags
```

---

# 4. Application à réaliser

## 4.1 Nom de l’application

```text
Catalogue Console
```

## 4.2 Objectif fonctionnel

Créer une application console permettant de consulter et analyser un catalogue de produits et des commandes clients.

L’utilisateur doit pouvoir naviguer dans un menu texte et déclencher différents traitements.

Exemple de menu attendu :

```text
=== CATALOGUE CONSOLE ===

1. Afficher tous les produits
2. Afficher les produits triés par prix croissant
3. Afficher les produits d'une catégorie
4. Rechercher les produits en promotion
5. Afficher les noms des produits en majuscules
6. Afficher les 3 produits les plus chers
7. Calculer le prix total du catalogue
8. Afficher les catégories uniques
9. Afficher les commandes avec leurs produits
10. Calculer le total de chaque commande
11. Vérifier si tous les produits ont un prix positif
12. Démonstration parallelStream
0. Quitter
```

---

# 5. Modèle de données attendu

## 5.1 Classe `Produit`

Champs obligatoires :

```java
private final String id;
private final String nom;
private final String categorie;
private final double prix;
private final boolean promotion;
```

Méthodes attendues :

```java
getId()
getNom()
getCategorie()
getPrix()
isPromotion()
toString()
```

## 5.2 Classe `Client`

Champs obligatoires :

```java
private final String id;
private final String nom;
private final String email;
```

## 5.3 Classe `LigneCommande`

Champs obligatoires :

```java
private final Produit produit;
private final int quantite;
```

## 5.4 Classe `Commande`

Champs obligatoires :

```java
private final String id;
private final Client client;
private final List<LigneCommande> lignes;
```

## 5.5 Données de départ

Prévoir au minimum :

- 12 produits ;
- 4 catégories ;
- 4 clients ;
- 5 commandes ;
- au moins une commande contenant plusieurs produits ;
- au moins deux produits avec la même catégorie ;
- au moins deux produits en promotion ;
- au moins un prix supérieur à 500 €.

Les données peuvent être codées en dur dans une classe `DataFactory`.

---

# 6. Fonctionnalités obligatoires

## Fonctionnalité 1 — Affichage simple avec `Consumer`

Afficher tous les produits avec un `Consumer<Produit>`.

Attendu :

```java
Consumer<Produit> afficherProduit = System.out::println;
produits.forEach(afficherProduit);
```

Vous pouvez adapter le format.

---

## Fonctionnalité 2 — Formatage avec `Function`

Créer une `Function<Produit, String>` qui transforme un produit en texte lisible.

Exemple attendu :

```text
[INFORMATIQUE] Clavier — 45.00 €
```

---

## Fonctionnalité 3 — Filtrage avec `Predicate`

Créer au moins trois prédicats :

```java
Predicate<Produit> estEnPromotion;
Predicate<Produit> prixSuperieurA100;
Predicate<Produit> categorieInformatique;
```

Les utiliser dans des pipelines Stream.

---

## Fonctionnalité 4 — Filtrage multiple

Afficher les produits :

- en promotion ;
- dont le prix est supérieur à 100 €.

Utiliser deux appels successifs à `filter`.

---

## Fonctionnalité 5 — Transformation avec `map`

Afficher uniquement les noms des produits en majuscules.

Attendu :

```java
produits.stream()
    .map(Produit::getNom)
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

---

## Fonctionnalité 6 — Tri avec `Comparator`

Afficher les produits triés :

1. par prix croissant ;
2. par prix décroissant ;
3. par catégorie puis par nom.

Utiliser :

```java
Comparator.comparing()
thenComparing()
reversed()
```

---

## Fonctionnalité 7 — `distinct` et collecte vers `Set`

Afficher toutes les catégories uniques.

Deux versions attendues :

1. avec `distinct()` ;
2. avec `Collectors.toSet()`.

---

## Fonctionnalité 8 — `limit` et top 3

Afficher les 3 produits les plus chers.

---

## Fonctionnalité 9 — `skip` et pagination

Afficher une page de produits.

Exemple :

- page 1 : produits 1 à 5 ;
- page 2 : produits 6 à 10.

Utiliser :

```java
skip()
limit()
```

---

## Fonctionnalité 10 — `reduce`

Calculer le prix total du catalogue avec `reduce`.

---

## Fonctionnalité 11 — Somme optimisée

Calculer le prix total du catalogue avec :

```java
mapToDouble()
sum()
```

Comparer brièvement les deux approches dans le `README.md`.

---

## Fonctionnalité 12 — `anyMatch`, `allMatch`, `findFirst`, `max`

Implémenter les traitements suivants :

- vérifier s’il existe un produit en promotion ;
- vérifier si tous les produits ont un prix positif ;
- trouver le premier produit de la catégorie `Informatique` ;
- trouver le produit le plus cher.

---

## Fonctionnalité 13 — `flatMap`

À partir des commandes, afficher tous les produits commandés dans une seule liste plate.

Exemple attendu :

```java
commandes.stream()
    .flatMap(c -> c.getLignes().stream())
    .map(LigneCommande::getProduit)
    .forEach(System.out::println);
```

---

## Fonctionnalité 14 — Total par commande

Calculer le total de chaque commande.

Utiliser une `BiFunction<Produit, Integer, Double>` ou une méthode équivalente.

Exemple :

```java
BiFunction<Produit, Integer, Double> calculLigne =
    (produit, quantite) -> produit.getPrix() * quantite;
```

---

## Fonctionnalité 15 — Collecte vers `Map`

Créer une `Map<String, Double>` où :

- la clé est l’identifiant de commande ;
- la valeur est le total de la commande.

Utiliser :

```java
Collectors.toMap()
```

---

## Fonctionnalité 16 — `Supplier`

Créer un `Supplier<String>` qui génère un identifiant de commande.

Exemple :

```java
Supplier<String> idGenerator = () -> UUID.randomUUID().toString();
```

L’utiliser au moins une fois dans le programme.

---

## Fonctionnalité 17 — `UnaryOperator`

Créer un `UnaryOperator<String>` pour nettoyer une saisie utilisateur.

Exemple :

- suppression des espaces avant/après ;
- passage en minuscules.

```java
UnaryOperator<String> nettoyerSaisie = s -> s.trim().toLowerCase();
```

---

## Fonctionnalité 18 — Références de méthodes

Utiliser au moins 5 références de méthodes dans le projet.

Exemples :

```java
System.out::println
Produit::getNom
Produit::getPrix
String::toUpperCase
LigneCommande::getProduit
```

---

## Fonctionnalité 19 — Variable effectivement finale

Créer un exemple simple dans le code ou dans les tests montrant l’utilisation d’une variable locale capturée par une lambda.

Exemple :

```java
String prefixe = "[PRODUIT] ";

produits.forEach(p -> System.out.println(prefixe + p.getNom()));
```

Ajouter un commentaire expliquant pourquoi `prefixe` doit être effectivement finale.

---

## Fonctionnalité 20 — `parallelStream`

Ajouter une démonstration de `parallelStream()`.

Elle doit :

- traiter une grande liste de nombres ;
- effectuer un calcul simple ou moyen ;
- afficher le temps en mode séquentiel ;
- afficher le temps en mode parallèle ;
- expliquer dans le `README.md` pourquoi le parallèle n’est pas toujours plus rapide.

Interdiction : modifier une liste externe partagée dans un `parallelStream`.

Mauvais exemple à ne pas faire :

```java
List<Integer> resultats = new ArrayList<>();

nombres.parallelStream()
    .forEach(n -> resultats.add(n * 2));
```

Bonne approche :

```java
List<Integer> resultats = nombres.parallelStream()
    .map(n -> n * 2)
    .toList();
```

---

# 7. Tests unitaires obligatoires

Créer au minimum 8 tests unitaires avec JUnit 5.

Tests minimum attendus :

1. filtrage des produits en promotion ;
2. filtrage des produits avec prix supérieur à 100 ;
3. transformation des noms en majuscules ;
4. calcul du total catalogue avec `reduce` ;
5. calcul du total catalogue avec `mapToDouble().sum()` ;
6. récupération des catégories uniques ;
7. calcul du total d’une commande ;
8. vérification du produit le plus cher.

Exemple :

```java
@Test
void doitTransformerNomEnMajuscules() {
    Function<Produit, String> nomMajuscule = p -> p.getNom().toUpperCase();

    Produit produit = new Produit("P1", "clavier", "Informatique", 45.0, false);

    assertEquals("CLAVIER", nomMajuscule.apply(produit));
}
```

---

# 8. Planning conseillé sur 7 heures

## Heure 1 — Mise en place

Objectifs :

- créer le dépôt public ;
- initialiser Maven ;
- créer `main`, `develop` et les premières branches `feature/*` ;
- créer le squelette du projet ;
- vérifier que `mvn clean test` fonctionne.

Livrable attendu en fin d’heure :

- dépôt public accessible ;
- projet Maven compilable ;
- premier commit propre.

---

## Heure 2 — Modèle métier et données

Objectifs :

- créer `Produit`, `Client`, `LigneCommande`, `Commande` ;
- créer `DataFactory` ;
- préparer les données de départ ;
- écrire les premiers tests simples.

Livrable attendu :

- modèles terminés ;
- données disponibles ;
- tests de base OK.

---

## Heure 3 — Services Stream niveau 1

Objectifs :

- `filter` ;
- `map` ;
- `Consumer` ;
- `Predicate` ;
- `Function` ;
- affichage simple ;
- produits en promotion ;
- produits par catégorie ;
- noms en majuscules.

Livrable attendu :

- premiers traitements fonctionnels ;
- menu console partiel.

---

## Heure 4 — Services Stream niveau 2

Objectifs :

- `sorted` ;
- `distinct` ;
- `limit` ;
- `skip` ;
- `Set` ;
- `Map` ;
- top 3 ;
- pagination ;
- catégories uniques.

Livrable attendu :

- traitements avancés sur produits ;
- tests associés.

---

## Heure 5 — Commandes et calculs

Objectifs :

- `flatMap` ;
- `reduce` ;
- `mapToDouble().sum()` ;
- `BiFunction` ;
- total par commande ;
- collecte vers `Map`.

Livrable attendu :

- calculs commande fonctionnels ;
- tests associés.

---

## Heure 6 — Console, tests et parallelStream

Objectifs :

- finaliser le menu console ;
- ajouter `Supplier` ;
- ajouter `UnaryOperator` pour les saisies ;
- ajouter la démonstration `parallelStream` ;
- compléter les tests unitaires.

Livrable attendu :

- application utilisable en console ;
- au moins 8 tests OK.

---

## Heure 7 — Stabilisation, Gitflow et restitution

Objectifs :

- fusionner les branches `feature/*` dans `develop` ;
- tester l’application complète ;
- corriger les bugs ;
- fusionner `develop` dans `main` ;
- créer le tag `v1.0.0`;
- finaliser le `README.md`.

Livrable attendu :

- version finale sur `main` ;
- tag `v1.0.0` ;
- README complet ;
- application exécutable.

---

# 9. README.md attendu

Le README doit contenir :

```markdown
# Catalogue Console

## Membres du groupe

- Nom Prénom promotion
- Nom Prénom promotion
- Nom Prénom promotion

## Description

Application console Java Maven permettant de manipuler un catalogue de produits et des commandes avec les lambdas et l'API Stream.

## Fonctionnalités

- Affichage des produits
- Filtrage
- Transformation
- Tri
- Distinct
- Pagination
- Calculs avec reduce
- Calculs avec mapToDouble
- Commandes avec flatMap
- Collecte vers Map
- Démonstration parallelStream

## Gitflow

Description rapide des branches utilisées.

## Lancer le projet

```bash
mvn clean compile
mvn test
mvn package
```

## Choix techniques

Expliquez brièvement :

- pourquoi utiliser `filter` ;
- pourquoi utiliser `map` ;
- pourquoi utiliser `flatMap` ;
- différence entre `reduce` et `mapToDouble().sum()` ;
- limite de `parallelStream`.

## Répartition du travail

Indiquez qui a fait quoi.

## Difficultés rencontrées

Indiquez les problèmes techniques rencontrés et les solutions apportées.
```

---

# 10. Critères d’évaluation

| Critère | Points |
|---|---:|
| Projet Maven fonctionnel | 2 |
| Dépôt Git public avec Gitflow respecté | 4 |
| Modèle métier propre | 3 |
| Application console utilisable | 4 |
| Utilisation correcte des lambdas | 4 |
| Utilisation correcte des interfaces fonctionnelles | 4 |
| Utilisation correcte des références de méthodes | 2 |
| Utilisation correcte des Streams | 6 |
| `flatMap`, `reduce`, `collect`, `Map` maîtrisés | 5 |
| Tests unitaires JUnit | 4 |
| Démonstration `parallelStream` correcte et sans effet de bord | 2 |
| README clair | 3 |
| Qualité globale du code | 3 |
| Total | 46 |


---

# 11. Points d’attention

## À faire

- nommer clairement les méthodes ;
- privilégier les pipelines lisibles ;
- tester les traitements métier ;
- faire des commits courts et compréhensibles ;
- documenter les choix dans le README.

## À éviter

- coder tout dans `Main.java` ;
- modifier une variable externe dans une lambda ;
- utiliser `parallelStream()` partout ;
- faire des branches sans pull request ;
- pousser directement dans `main` ;
- écrire des lambdas de 15 lignes ;
- confondre `map` et `flatMap`.

---

# 12. Livrables finaux

À rendre :

1. lien du dépôt Git public ;
2. branche `main` stable ;
3. branche `develop` visible ;
4. historique avec branches `feature/*` ;
5. tag `v1.0.0` ;
6. projet Maven complet ;
7. README.md ;
8. tests unitaires ;
9. application console exécutable.

---

# 13. Commande finale de vérification

Avant rendu, le groupe doit exécuter sur la branche 'main' les autres branches ne seront pas corrigées, tout les travaux devront donc être réintégrés:

```bash
mvn clean test
```

Puis lancer l’application console.

Aucun rendu ne sera considéré comme terminé si le projet ne compile pas.
