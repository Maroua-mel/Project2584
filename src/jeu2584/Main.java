package jeu2584;

import interface2584.MainFX;
import java.util.Scanner;

public class Main implements Parametres {

    public static void main(String[] args) {
        if (args.length == 0) {
            MainFX.main(args);
        } else {
            Grille g1 = new Grille(); //crée la grille du joueur 1
            boolean b = g1.nouvelleCase2584(); //ajoute deux cases à la grille du joueur 1
            b = g1.nouvelleCase2584();
            System.out.println("Grille joueur 1");
            System.out.println(g1);
            Grille g2 = new Grille(g1); //copie la grille du joueur 1
            System.out.println("Grille joueur 2");
            System.out.println(g2);
            Scanner sc = new Scanner(System.in);

            while (!g1.partieFinie2584() && !g2.partieFinie2584()) {
                for (int i = 0; i < 2; i++) {
                    System.out.println("Grille joueur " + (i + 1));
                    String[] t;
                    if (i == 0) {
                        System.out.println(g1);
                        t = new String[]{"d", "g", "h", "b"};
                    } else {
                        System.out.println(g2);
                        t = new String[]{"m", "k", "o", "l"};
                    }

                    System.out.println("Déplacer vers la Droite (" + t[0] + "), Gauche (" + t[1] + "), Haut (" + t[2] + "), ou Bas (" + t[3] + ") ?");
                    String s = sc.nextLine();
                    s.toLowerCase();
                    while (!(s.equals(t[0]) || s.equals("droite") || s.equals(t[1]) || s.equals("gauche") || s.equals(t[2]) || s.equals("haut") || s.equals(t[3])
                            || s.equals("bas"))) { //si entrée d'une touche non valide, le joueur boucle jusqu'à l'entrée d'une touche valide
                        System.out.println("Vous devez écrire " + t[0] + " pour Droite, " + t[1] + " pour Gauche, " + t[2] + " pour Haut ou " + t[3] + " pour Bas");
                        s = sc.nextLine();
                        s.toLowerCase();
                    }
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
                        b2 = g1.lanceurDeplacerCases2584(direction);
                    } else {
                        b2 = g2.lanceurDeplacerCases2584(direction);
                    }
                    if (b2) { //si au moins une case a été déplacée. Si le joueur ne déplace pas une case pendant son tour, il le passe
                        if (i == 0) {
                            b = g1.nouvelleCase2584();
                        } else if (i != 0) {
                            b = g2.nouvelleCase2584();
                        }
                        if (!b && i == 0) {
                            System.out.println(g1.gameOver2584());
                            System.exit(0);
                        } else if (!b && i != 0) {
                            System.out.println(g2.gameOver2584());
                            System.exit(0);
                        }
                    }

                    if (i == 0) {
                        System.out.println(g1);
                        System.out.println("Score du joueur 1 : " + g1.getScore());
                        System.out.println("");
                    } else {
                        System.out.println(g2);
                        System.out.println("Score du joueur 2 : " + g2.getScore());
                        System.out.println("");
                    }
                    if (g1.getValeurMax() >= OBJECTIF2584) {
                        System.out.println(g1.victory2584());
                        System.exit(0);
                    } else if (g2.getValeurMax() >= OBJECTIF2584) {
                        System.out.println(g2.victory2584());
                        System.exit(0);
                    }
                }
            }
        }
    }
}
