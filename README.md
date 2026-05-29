# Catalogue Console

Application console Java (Maven) pour gérer un catalogue de produits et des commandes
clients. Le projet sert de support pour pratiquer les lambdas, les interfaces fonctionnelles
et l'API Stream.

## Membres du groupe

- GORON Aurélien - ING4
- NSOKI Teddy - ING4
- SAFRANO Antonin - ING4

## Description

`Catalogue Console` permet de consulter et d'analyser un catalogue de produits et des
commandes via un menu texte. Chaque entrée du menu déclenche un traitement basé sur l'API
Stream (filtrage, transformation, tri, agrégation, etc.). Les données sont codées en dur
dans `DataFactory` : 12 produits, 6 catégories, 4 clients et 5 commandes.

## Lancer le projet

```bash
mvn clean compile      # compilation
mvn test               # tests unitaires (JUnit 5)
mvn package            # build du jar
```

## Fonctionnalités

Le menu console expose les traitements suivants :

| # menu | Traitement | Notions illustrées |
|---|---|---|
| 1 | Afficher tous les produits | `Function` (formatage) + variable effectivement finale |
| 2 | Produits triés par prix croissant | `sorted` + `Comparator` |
| 3 | Produits d'une catégorie | `filter` + `UnaryOperator` (nettoyage de la saisie) |
| 4 | Produits en promotion | `filter` + `Predicate` |
| 5 | Noms des produits en majuscules | `map` + références de méthodes |
| 6 | 3 produits les plus chers | `sorted` + `limit` |
| 7 | Prix total du catalogue | `mapToDouble().sum()` |
| 8 | Catégories uniques | `distinct` |
| 9 | Commandes et leurs produits | `flatMap` |
| 10 | Total de chaque commande | `BiFunction` |
| 11 | Tous les produits ont un prix positif | `allMatch` |
| 12 | Démonstration `parallelStream` | séquentiel vs parallèle |
| 0 | Quitter | Sortie du programme |

D'autres traitements sont implémentés dans les services et couverts par les tests sans être exposés directement au menu

## Architecture

```
src/main/java/fr/ecole/tp/
├── Main.java                      # point d'entrée
├── Console.java                   # menu texte et boucle d'interaction
├── model/                         # Produit, Client, LigneCommande, Commande, DataFactory
├── service/                       # ProduitService, CommandeService, ParallelStreamService
└── util/                          # ProduitFormatter (Consumer + Function)
```

## Gitflow

Le dépôt suit le modèle Gitflow :

- `main` : version stable (recevra la version finale et le tag `v1.0.0`) ;
- `develop` : intégration du travail validé ;
- `feature/*` : une fonctionnalité par branche, fusionnée dans `develop` via pull request.

Branches utilisées :

```
main
develop
feature/modeles            # modèle métier et données
feature/services-stream    # traitements Stream
feature/console            # menu console
feature/tests              # tests unitaires
```

## Choix techniques

`filter` sélectionne les produits qui valident un `Predicate` (promotion, prix, catégorie) sans modifier la liste d'origine.

`map` transforme chaque produit en une autre valeur, par exemple en chaîne formatée.

`flatMap` aplatit les lignes de toutes les commandes en un seul flux de produits. Là où `map` produirait un `Stream<Stream<Produit>>`, flatMap` fusionne tout en un `Stream<Produit>` unique.

`reduce` et `mapToDouble().sum()` calculent tous les deux le prix total du catalogue. `reduce` est générique et accepte n'importe quel accumulateur, mais il travaille sur des `Double`. `mapToDouble().sum()` utilise un `DoubleStream` : code plus lisible et plus rapide pour une simple somme de `double`.

`parallelStream` n'est pas toujours plus rapide. Répartir le travail sur plusieurs threads puis fusionner les résultats a un coût fixe. Pour une petite liste ou une opération peu coûteuse (ici `n * 2`), ce n'est pas rentable. La démonstration (10 millions d'éléments) montre que le séquentiel est souvent aussi rapide, parfois plus. Le parallèle devient intéressant sur de grandes listes avec des gros calculs.

## Répartition du travail

| Rôle | Responsabilités | Étudiant |
|---|---|---|
| Référent modèle / données | Classes métier, `DataFactory`, Gitflow, `Console` | Teddy |
| Référent traitements Stream / tests | `ProduitService` , tests | Antonin |
| Référent traitements Stream / tests | `CommandeService`, tests | Aurélien |

## Difficultés rencontrées

Pagination : le pipeline formatait d'abord toute la liste avant de paginer. En effet, on avait le `map` avant `skip` et `limit`. On l'a corrigé en `skip` puis `limit` puis `map`, pour ne formater que la page demandée.

Cohérence des noms : quelques méthodes ont été renommées pour respecter le camelCase et la grammaire, par exemple `afficherToutProduits` devenu `afficherTousProduits`.

`parallelStream` : sur notre cas le parallèle était plus lent, car l'opération était très simple. On a augmenté la taille de la liste pour que la mesure soit représentative.

## Corrections apportées en fin de TP

En relisant le sujet à la fin du TP, on s'est rendu compte que des fonctionnalités attenduesétaient manquantes ou incomplètes. On a donc repris le code après coup pour les ajouter et corriger plusieurs bugs.

Option « 0. Quitter » sans effet : le choix `0` ne quittait pas et rebouclait à l'infini. On ajouté un `case 0` qui termine le programme.

Bug dans la génération de nombres de la démo `parallelStream` : la valeur aléatoire était calculée puis ignorée, l'indice de boucle étant ajouté à la place (missinput). C'est corrigé, et la liste est passée à 10 millions d'éléments pour une mesure pertinente.