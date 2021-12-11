package jeu2584;

import java.io.Serializable;
import static jeu2584.Parametres.BAS;
import static jeu2584.Parametres.DROITE;
import static jeu2584.Parametres.GAUCHE;
import static jeu2584.Parametres.HAUT;
import static jeu2584.Parametres.SUITE2;
import static jeu2584.Parametres.TAILLE;

public class Case implements Parametres, Serializable {

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

    /** Setter of Grille */
    public void setGrille(Grille g) {
        this.grille = g;
    }
    /** Getter of the abscissa of the tile
     *@return returns the int of the tile's abscissa
     */
    public int getX() {
        return this.x;
    }

    /** Getter of the ordinate of the tile
     *@return returns the int of the tile's ordinate
     */
    public int getY() {
        return this.y;
    }

    /** Setter of the abscissa of the tile */
    public void setX(int x) {
        this.x = x;
    }

    /** Setter of the ordinate of the tile */
    public void setY(int y) {
        this.y = y;
    }

    /** Setter of the value of the tile */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /** Getter of the value of the tile */
    public int getValeur() {
        return this.valeur;
    }

    /**
     * Returns true if and only if there's not an other instance of the given object.
     * @param obj The object whose values are going to be compared to see if it's the only one
     * @return returns true if this object has the same hashcode as an other object,
     * false if it's the only one.
     */
    @Override
    public boolean equals(Object obj) { // la méthode equals est utilisée lors de l'ajout d'une case à un ensemble pour vérifier qu'il n'y a pas de doublons
        if (obj instanceof Case) { // (teste parmi tous les candidats qui ont le même hashcode)
            Case c = (Case) obj;
            return (this.x == c.x && this.y == c.y);
        } else {
            return false;
        }
    }

    /**
     * Returns an int which represents the hashcode of the abscissa and the ordinate.
     * @return returns the int of the hashcode
     */
    @Override
    public int hashCode() {
        return this.x * 7 + this.y * 13;
    }

    /**
     * Returns true if and only if the argument is not null and represents same value as this tile.
     * @param c The tile that we want to compare with this instance
     * @return returns true if this object represents the same tile value as the argument,
     * false if this object doesn't represent the same value.
     */
    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }

    /**
     * Returns true if and only if the number of a given tile is present in the Fibonacci Sequence (SUITE2).
     * @param c The tile whose number we want to search in SUITE2
     * @return returns true if the number of the given tile is present in SUITE2,
     * false if the number of the tile is not found in SUITE2
     */
    public boolean valeur2584(Case c) {
        if (c != null) {
            return SUITE2.contains(this.valeur + c.valeur);
        } else {
            return false;
        }
    }

    /**
     * Returns the nearest tile in a given direction.
     * @param direction The direction in which we are going to search the nearest tile
     * @return returns the nearest tile found in the given direction,
     * if there's no tile met, return null
     */
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

    /**
     * Returns the String of the hex color code of each tile for the chosen style of graphic interface.
     * @param b An int which represents one of the three styles of graphic interface possibilities
     * @return returns a string that represents a hex color code for each tile.
     */
    public String detCouleur(int b) {
        switch (b) {
            case 1:
                switch (this.valeur) {
                    case 2:
                        return "d99ba1";
                    case 3:
                        return "d99ba1";
                    case 5:
                        return "d99ba1";
                    case 8:
                        return "d99ba1";
                    case 13:
                        return "d99ba1";
                    case 21:
                        return "d99ba1";
                    case 34:
                        return "d99ba1";
                    case 55:
                        return "d99ba1";
                    case 89:
                        return "d99ba1";
                    case 144:
                        return "d99ba1";
                    case 233:
                        return "d99ba1";
                    case 377:
                        return "d99ba1";
                    case 610:
                        return "d99ba1";
                    case 987:
                        return "d99ba1";
                    case 1597:
                        return "d99ba1";
                    case 2584:
                        return "d99ba1";
                    default:
                        return "d99ba1";
                }
            case 2:
                switch (this.valeur) {
                    case 2:
                        return "ffd90f";
                    case 3:
                        return "ffd90f";
                    case 5:
                        return "ffd90f";
                    case 8:
                        return "ffd90f";
                    case 13:
                        return "ffd90f";
                    case 21:
                        return "ffd90f";
                    case 34:
                        return "ffd90f";
                    case 55:
                        return "ffd90f";
                    case 89:
                        return "ffd90f";
                    case 144:
                        return "ffd90f";
                    case 233:
                        return "ffd90f";
                    case 377:
                        return "ffd90f";
                    case 610:
                        return "ffd90f";
                    case 987:
                        return "ffd90f";
                    case 1597:
                        return "ffd90f";
                    case 2584:
                        return "ffd90f";
                    default:
                        return "ffd90f";
                }
            default:
                switch (this.valeur) {
                    case 2:
                        return "ede0c8";
                    case 3:
                        return "f2b179";
                    case 5:
                        return "f59563";
                    case 8:
                        return "f67c60";
                    case 13:
                        return "f65e3b";
                    case 21:
                        return "edcf73";
                    case 34:
                        return "edcc62";
                    case 55:
                        return "edc850";
                    case 89:
                        return "edc53f";
                    case 144:
                        return "edc22d";
                    case 233:
                        return "e8ed2d";
                    case 377:
                        return "f0f373";
                    case 610:
                        return "f8f9b9";
                    case 987:
                        return "b9f8f9";
                    case 1597:
                        return "b9e8f9";
                    case 2584:
                        return "b9d8f9";
                    default:
                        return "eee4da";
                }
        }
    }


    /**
     * Returns the String which indicates the abscissa, the ordinate and the value of this tile.
     * @return returns a string that indicates the abscissa, the ordinate and the value of this tile.
     */
    @Override
    public String toString() {
        return "Case(" + this.x + "," + this.y + "," + this.valeur + ")";
    }
}
