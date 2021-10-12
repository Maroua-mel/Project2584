package jeu2584;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import static jeu2584.Parametres.BAS;
import static jeu2584.Parametres.DROITE;
import static jeu2584.Parametres.GAUCHE;
import static jeu2584.Parametres.HAUT;
import static jeu2584.Parametres.TAILLE;

public class Grille implements Parametres {

    private final HashSet<Case> grille;
    private int valeurMax = 0, score = 0;
    private boolean deplacement;

    public Grille() {
        this.grille = new HashSet<>();
    }

    public Grille(Grille g) { //permet de cloner une grille
        this.grille = new HashSet<>();
        for (Case c : g.grille) {
            Case c1 = new Case(c, this);
            this.grille.add(c1);
        }
        this.score = g.score;
        this.deplacement = g.deplacement;
    }

    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "\n";
        }
        return result;
    }

    public String toHTML() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
    }
        String result = "<html>";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "<br/>";
    }
        result += "</html>";
        return result;
    }

    public HashSet<Case> getGrille() {
        return grille;
    }

    public int getValeurMax() {
        return valeurMax;
    }

    public int getScore() {
        return score;
    }

    public boolean partieFinie2584() {
        if (this.grille.size() < TAILLE * TAILLE) {
            return false;
        } else {
            for (Case c : this.grille) {
                for (int i = 1; i <= 2; i++) {
                    if (c.getVoisinDirect(i) != null) {
                        if (c.valeur2584(c.getVoisinDirect(i))) {
                            return false;
                        }
                    }
                }
            }
        }
        this.gameOver2584();
        return true;
    }

    // lanceur de la méthode récursive (les paramètres à passer à cette dernière ne sont pas les mêmes suivant la direction). Retourne 
    //vrai si au moins une case à bouger (pour éviter de rajouter une nouvelle case si aucun déplacement n’a été possible dans cette direction)
    public boolean lanceurDeplacerCases2584(int direction) {
        Case[] extremites = this.getCasesExtremites(direction);
        deplacement = false; // pour vérifier si on a bougé au moins une case après le déplacement, avant d'en rajouter une nouvelle
        for (int i = 0; i < TAILLE; i++) {
            switch (direction) {
                case HAUT:
                    this.deplacerCasesRecursif2584(extremites, i, direction, 0);
                    break;
                case BAS:
                    this.deplacerCasesRecursif2584(extremites, i, direction, 0);
                    break;
                case GAUCHE:
                    this.deplacerCasesRecursif2584(extremites, i, direction, 0);
                    break;
                default:
                    this.deplacerCasesRecursif2584(extremites, i, direction, 0);
                    break;
            }
        }
        return deplacement;
    }

    // additionne les valeurs des cases passées en paramètre et met éventuellement à jour la variable valeurMax
    private void fusion2584(Case c, Case c2) {
        score += (c.getValeur() + c2.getValeur());
        c.setValeur(c.getValeur() + c2.getValeur());
        if (this.valeurMax < c.getValeur()) {
            this.valeurMax = c.getValeur();
        }
        deplacement = true;
    }

    //on commence par déplacer les cases les plus proches de la direction choisie. Puis, pour chacune de ces cases,
    //on récupère le voisin direct dans la direction opposée, et on le déplace (en vérifiant s’il faut fusionner des cases ou juste déplacer).
    //On continue récursivement jusqu’à ce qu’il n’y ait plus de voisin. Pour éviter les problèmes de doublon avec l’ensemble de cases 
    //(variable grille), lors d’un déplacement il faut retirer la case de la grille, modifier ses coordonnées, la remettre dans la grille au bon endroit.
    private void deplacerCasesRecursif2584(Case[] extremites, int rangee, int direction, int compteur) {
        if (extremites[rangee] != null) {
            if ((direction == HAUT && extremites[rangee].getY() != compteur)
                    || (direction == BAS && extremites[rangee].getY() != TAILLE - 1 - compteur)
                    || (direction == GAUCHE && extremites[rangee].getX() != compteur)
                    || (direction == DROITE && extremites[rangee].getX() != TAILLE - 1 - compteur)) {
                this.grille.remove(extremites[rangee]);
                switch (direction) {
                    case HAUT:
                        extremites[rangee].setY(compteur);
                        break;
                    case BAS:
                        extremites[rangee].setY(TAILLE - 1 - compteur);
                        break;
                    case GAUCHE:
                        extremites[rangee].setX(compteur);
                        break;
                    default:
                        extremites[rangee].setX(TAILLE - 1 - compteur);
                        break;
                }
                this.grille.add(extremites[rangee]);
                deplacement = true;
            }
            Case voisin = extremites[rangee].getVoisinDirect(-direction);
            if (voisin != null) {
                if (extremites[rangee].valeur2584(voisin)) {
                    this.fusion2584(extremites[rangee], voisin);
                    extremites[rangee] = voisin.getVoisinDirect(-direction);
                    this.grille.remove(voisin);
                    this.deplacerCasesRecursif2584(extremites, rangee, direction, compteur + 1);
                } else {
                    extremites[rangee] = voisin;
                    this.deplacerCasesRecursif2584(extremites, rangee, direction, compteur + 1);
                }
            }
        }
    }

    /*
    * Si direction = HAUT : retourne les 4 cases qui sont le plus en haut (une pour chaque colonne)
    * Si direction = DROITE : retourne les 4 cases qui sont le plus à droite (une pour chaque ligne)
    * Si direction = BAS : retourne les 4 cases qui sont le plus en bas (une pour chaque colonne)
    * Si direction = GAUCHE : retourne les 4 cases qui sont le plus à gauche (une pour chaque ligne)
    * Attention : le tableau retourné peut contenir des null si les lignes/colonnes sont vides
     */
    // retourne sous forme d’un tableau les 4 cases les plus proches de la direction choisie. Dans l’exemple ci-dessous, si la direction est droite, les 
    //cases à l’extrémité sont en rouge. Comme la deuxième ligne est vide, la deuxième case du tableau contient null.
    public Case[] getCasesExtremites(int direction) {
        Case[] result = new Case[TAILLE];
        for (Case c : this.grille) {
            switch (direction) {
                case HAUT:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() > c.getY())) { // si on n'avait pas encore de case pour cette rangée ou si on a trouvé un meilleur candidat
                        result[c.getX()] = c;
                    }
                    break;
                case BAS:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() < c.getY())) {
                        result[c.getX()] = c;
                    }
                    break;
                case GAUCHE:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() > c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
                default:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() < c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
            }
        }
        return result;
    }

    public String victory2584() {
        return "Bravo ! Vous avez atteint " + this.valeurMax + ".";
    }

    public String gameOver2584() {
        return "La partie est finie. Votre score est de " + this.score + ".";
    }

    // à condition qu’il reste des emplacements vides dans la grille, positionne aléatoirement (là où il n’y a pas déjà une case) une case avec une 
    //valeur aléatoire qui peut être soit 2, soit 4. La méthode nouvelleCase retourne un booléen, selon si elle a réussi à ajouter une case ou pas
    public boolean nouvelleCase2584() {
        if (this.grille.size() < TAILLE * TAILLE) {
            ArrayList<Case> casesLibres = new ArrayList<>();
            Random ra = new Random();
            int valeur = 1;
            double ra1 = Math.random();
            if (ra1 > 0.75) {
                valeur = 2;
            }
            // on crée toutes les cases encore libres
            for (int x = 0; x < TAILLE; x++) {
                for (int y = 0; y < TAILLE; y++) {
                    Case c = new Case(x, y, valeur);
                    if (!this.grille.contains(c)) { // contains utilise la méthode equals dans Case
                        casesLibres.add(c);
                    }
                }
            }
            // on en choisit une au hasard et on l'ajoute à la grille
            Case ajout = casesLibres.get(ra.nextInt(casesLibres.size()));
            ajout.setGrille(this);
            this.grille.add(ajout);
            if ((this.grille.size() == 1) || (this.valeurMax == 2 && ajout.getValeur() == 4)) { // Mise à jour de la valeur maximale présente dans la grille si c'est la première case ajoutée ou si on ajoute un 4 et que l'ancien max était 2
                this.valeurMax = ajout.getValeur();
            }
            return true;
        } else {
            return false;
        }
    }

    public Case nouvelleCase2584GUI() {
        if (this.grille.size() < TAILLE * TAILLE) {
            ArrayList<Case> casesLibres = new ArrayList<>();
            Random ra = new Random();
            int valeur = 1;
            double ra1 = Math.random();
            if (ra1 > 0.75) {
                valeur = 2;
            }
            // on crée toutes les cases encore libres
            for (int x = 0; x < TAILLE; x++) {
                for (int y = 0; y < TAILLE; y++) {
                    Case c = new Case(x, y, valeur);
                    if (!this.grille.contains(c)) { // contains utilise la méthode equals dans Case
                        casesLibres.add(c);
                    }
                }
            }
            // on en choisit une au hasard et on l'ajoute à la grille
            Case ajout = casesLibres.get(ra.nextInt(casesLibres.size()));
            ajout.setGrille(this);
            this.grille.add(ajout);
            if ((this.grille.size() == 1) || (this.valeurMax == 2 && ajout.getValeur() == 4)) { // Mise à jour de la valeur maximale présente dans la grille si c'est la première case ajoutée ou si on ajoute un 4 et que l'ancien max était 2
                this.valeurMax = ajout.getValeur();
            }
            return ajout;
        } else {
            return null;
        }
    }
}
