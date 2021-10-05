package jeu2584;

import java.util.Scanner;

public class Main implements Parametres {

    public static void main(String[] args) {
        Grille g = new Grille();
        boolean b = g.nouvelleCase2584();
        b = g.nouvelleCase2584();
        System.out.println("Grille joueur 1");
        System.out.println(g);
        Grille g1 = new Grille(g);
        System.out.println("Grille joueur 2");
        System.out.println(g1);
        Scanner sc = new Scanner(System.in);

        while (!g.partieFinie2584() && !g1.partieFinie2584()) {
            for (int i = 0; i < 2; i++) {
                System.out.println("Grille joueur " + (i + 1));
                String[] t = new String[4];
                if (i == 0) {
                    System.out.println(g);
                    t = new String[]{"d", "g", "h", "b"};
                } else {
                    System.out.println(g1);
                    t = new String[]{"m", "k", "o", "l"};
                }

                System.out.println("Déplacer vers la Droite (" + t[0] + "), Gauche (" + t[1] + "), Haut (" + t[2] + "), ou Bas (" + t[3] + ") ?");
                String s = sc.nextLine();
                s.toLowerCase();
                //si entrée d'une touche non valide, le joueur passe son tour ou boucle jusqu'à l'entrée d'une touche valide
                if (!(s.equals(t[0]) || s.equals("droite") || s.equals(t[1]) || s.equals("gauche") || s.equals(t[2]) || s.equals("haut") || s.equals(t[3])
                        || s.equals("bas"))) {
                    System.out.println("Vous devez écrire " + t[0] + " pour Droite, " + t[1] + " pour Gauche, " + t[2] + " pour Haut ou " + t[3] + " pour Bas");
                } else {
                    int direction;
                    if (s.equals(t[0]) || s.equals("droite")) {
                        direction = DROITE;
                    } else if (s.equals(t[1]) || s.equals("gauche")) {
                        direction = GAUCHE;
                    } else if (s.equals(t[2]) || s.equals("haut")) {
                        direction = HAUT;
                    } else {
                        direction = BAS;
                    }

                    Boolean b2 = null;
                    if (i == 0) {
                        b2 = g.lanceurDeplacerCases2584(direction);
                    } else {
                        b2 = g1.lanceurDeplacerCases2584(direction);
                    }
                    if (b2) {
                        if (i == 0) {
                            b = g.nouvelleCase2584();
                        } else {
                            b = g1.nouvelleCase2584();
                        }
                        if (!b) {
                            g.gameOver();
                        }
                    }
                }
                if (i == 0) {
                    System.out.println(g);
                    System.out.println(g.getScore());
                } else {
                    System.out.println(g1);
                    System.out.println(g1.getScore());
                }
                if (g.getValeurMax() >= OBJECTIF2584) {
                    g.victory();
                } else if (g1.getValeurMax() >= OBJECTIF2584) {
                    g1.victory();
                }
            }
        }
        g.gameOver();
    }

}
