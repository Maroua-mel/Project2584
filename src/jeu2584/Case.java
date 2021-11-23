package jeu2584;

import static jeu2584.Parametres.BAS;
import static jeu2584.Parametres.DROITE;
import static jeu2584.Parametres.GAUCHE;
import static jeu2584.Parametres.HAUT;
import static jeu2584.Parametres.SUITE2;
import static jeu2584.Parametres.TAILLE;

public class Case implements Parametres {

    private int x, y, valeur;
    private Grille grille;

    public Case(int abs, int ord, int v) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
    }

    public Case(Case c, Grille g) { //permet de cloner une case
        this.x = c.x;
        this.y = c.y;
        this.valeur = c.valeur;
        this.grille = g;
    }

    public void setGrille(Grille g) {
        this.grille = g;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return this.valeur;
    }

    @Override
    public boolean equals(Object obj) { // la méthode equals est utilisée lors de l'ajout d'une case à un ensemble pour vérifier qu'il n'y a pas de doublons
        if (obj instanceof Case) { // (teste parmi tous les candidats qui ont le même hashcode)
            Case c = (Case) obj;
            return (this.x == c.x && this.y == c.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() { // détermine le hashcode
        return this.x * 7 + this.y * 13;
    }

    //teste si la case courante a la même valeur que la case passée en paramètre et retourne un booléen
    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }

    public boolean valeur2584(Case c) {
        if (c != null) {
            return SUITE2.contains(this.valeur + c.valeur);
        } else {
            return false;
        }
    }

    //prend en paramètre une direction et retourne la première case rencontrée dans cette direction en partant de la case courante
    public Case getVoisinDirect(int direction) {
        switch (direction) {
            case HAUT:
                for (int i = this.y - 1; i >= 0; i--) {
                    for (Case c : grille.getGrille()) {
                        if (c.getX() == this.x && c.getY() == i) {
                            return c;
                        }
                    }
                }
                break;
            case BAS:
                for (int i = this.y + 1; i < TAILLE; i++) {
                    for (Case c : grille.getGrille()) {
                        if (c.getX() == this.x && c.getY() == i) {
                            return c;
                        }
                    }
                }
                break;
            case GAUCHE:
                for (int i = this.x - 1; i >= 0; i--) {
                    for (Case c : grille.getGrille()) {
                        if (c.getX() == i && c.getY() == this.y) {
                            return c;
                        }
                    }
                }
                break;
            case DROITE:
                for (int i = this.x + 1; i < TAILLE; i++) {
                    for (Case c : grille.getGrille()) {
                        if (c.getX() == i && c.getY() == this.y) {
                            return c;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return null;
    }

    //choisit de déterminer la couleur avec une fonction, dans le controlleur. Possibilité alternative de directement mettre un attribut couleur dans
    //la case
    public String detCouleur() {
        return "";
    }

    @Override
    public String toString() {
        return "Case(" + this.x + "," + this.y + "," + this.valeur + ")";
    }
}
