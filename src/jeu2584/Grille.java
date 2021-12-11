package jeu2584;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import static jeu2584.Parametres.BAS;
import static jeu2584.Parametres.DROITE;
import static jeu2584.Parametres.GAUCHE;
import static jeu2584.Parametres.HAUT;
import static jeu2584.Parametres.TAILLE;

public class Grille implements Parametres, Serializable {

    private HashSet<Case> grille;
    private int valeurMax = 0, score = 0;
    private boolean deplacement;

    public Grille() {
        this.grille = new HashSet<>();
    }


    /*
    * Allows you to clone a grid
    * @param g (Grille object)
    */
    public Grille(Grille g) { 
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

    /*
    * Return grille
    * Getteur of grille
    */
    public HashSet<Case> getGrille() {
        return grille;
    }

	/*
    * Return valeurMax
    * Getteur of valeurMax
    */
    public int getValeurMax() {
        return valeurMax;
    }

    /*
    * Return score
    * Getteur of score
    */
    public int getScore() {
        return score;
    }

    public void setGrille(HashSet<Case> ens) {
        this.grille = ens;
    }

    /*
    * 
    * Setteur of score
    */
    public void setScore(int s) {
        this.score = s;
    }

    /*
    * Copy current grid
    * @param ensTemp (Case object from abstract class HashSet)	
    */
    public void copierGrille(HashSet<Case> ensTemp) { 
        for (Case c : grille) {
            Case c1 = new Case(c, this);
            ensTemp.add(c1);
        }
    }

    /*
    * Return a boolean 
    * Find out if the game is over or not
    * 
    */    
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

    
    /*
    * Return a boolean, true if at least one tile to move
    * Recursive method, several parameters on the value of the direction
    */
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

    /*
    * Add values of tiles passed on paramter and updates variable valueMax
    * @param c (Case object), c2 (object c2)
    */
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
                    int x = extremites[rangee].getX();
                    int y = extremites[rangee].getY();
                    extremites[rangee] = voisin.getVoisinDirect(-direction);
                    this.grille.remove(voisin);
                    voisin.setX(x);
                    voisin.setY(y);
                    this.deplacerCasesRecursif2584(extremites, rangee, direction, compteur + 1);
                } else {
                    extremites[rangee] = voisin;
                    this.deplacerCasesRecursif2584(extremites, rangee, direction, compteur + 1);
                }
            }
        }
    }

    /*
    * Return an array of 4 tiles closest to the chosen direction
    * @param direction(int)
    * If direction = HAUT : return 4 tiles  who are the most up (one for each column)
    * If direction = DROITE : return 4 tiles  who are the most right (one for each line)
    * If direction = BAS : return 4 tiles  who are the most low (one for each column)
    * If direction = GAUCHE : return 4 tiles  who are the most left (one for each line)
    * The returned array can contain nulls if the line/columns are empty
     */
    public Case[] getCasesExtremites(int direction) {
        Case[] result = new Case[TAILLE];
        for (Case c : this.grille) {
            switch (direction) {
                case HAUT:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() > c.getY())) { 
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

    /*
    * Return a victory message
    *
    */
    public String victory2584() {
        return "Bravo ! Vous avez atteint " + this.valeurMax + ".";
    }

    /*
    * Return a defeat message
    *
    */
    public String gameOver2584() {
        return "La partie est finie. Votre score est de " + this.score + ".";
    }

    /*
    * Return a boolean
    * Add a random tiles in an empty space with a random value ( 1 or 2) if t there are empty spaces in the grid
    *
    */
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
            if ((this.grille.size() == 1) || (this.valeurMax == 2 && ajout.getValeur() == 3)) { // Mise à jour de la valeur maximale présente dans la grille
                this.valeurMax = ajout.getValeur(); //si c'est la première case ajoutée ou si on ajoute un 3 et que l'ancien max était 2
            }
            return true;
        } else {
            return false;
        }
    }
}
