package jeu2584;

import java.util.Arrays;
import java.util.HashSet;

public interface Parametres {
    static final int HAUT = 1;
    static final int DROITE = 2;
    static final int BAS = -1;
    static final int GAUCHE = -2;
    static final int TAILLE = 4;
    static final int OBJECTIF2584 = 2584;
    static final Integer[] SUITE = new Integer[] {2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584};
    static final HashSet<Integer> SUITE2 = new HashSet<>(Arrays.asList(SUITE));
}