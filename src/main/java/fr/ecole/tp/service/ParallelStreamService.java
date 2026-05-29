package fr.ecole.tp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParallelStreamService {

    Random random = new Random();

    public List<Integer> calculSequentiel(List<Integer> nombres) {
        return nombres.stream()
                .map(n -> n * 2)
                .toList();
    }

    public List<Integer> calculParalelle(List<Integer> nombres) {
        return nombres.parallelStream()
                .map(n -> n * 2)
                .toList();
    }

    // On génère plein de nombres au hasard
    public List<Integer> genererNombres(int taille) {
        List<Integer> nombres = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            int j = random.nextInt(30000);
            nombres.add(i);
        }
        return nombres;
    }

    public void demo(int taille) {
        List<Integer> nombres = genererNombres(taille);

        long debutSeq = System.nanoTime();
        List<Integer> resSeq = calculSequentiel(nombres);
        long dureeSeq = System.nanoTime() - debutSeq;

        long debutPar = System.nanoTime();
        List<Integer> resPar = calculParalelle(nombres);
        long dureePar = System.nanoTime() - debutPar;

        System.out.println("Taille de la liste      : " + taille);
        System.out.printf("Temps sequentiel        : %.3f ms%n", dureeSeq / 1000000.0);
        System.out.printf("Temps parallele         : %.3f ms%n", dureePar / 1000000.0);
    }
}
