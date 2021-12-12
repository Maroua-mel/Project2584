package jeu2584;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OrdiAleatoire extends Joueur {

    /**
     * Duplicates the grid for the second player.
     * @param gM A Grille object
     * @param g The gridpane of the game's grid
     * @param b The button to cancel player's last move
     * @param c The chosen style of graphic interface
     */
    public OrdiAleatoire(Grille gM, GridPane g, Label s, Label hS, Button b, int c) { //permet de construire un joueur avec une grille identique à celle d'un autre
        super.grilleModele = new Grille(gM); //copie la grille
        super.grillePrec = new Grille();
        super.grilleAffichage = g;
        super.style = c;
        super.labelScore = s;
        super.labelHScore = hS;
        super.buttonAnnuler = b;
        super.buttonAnnuler.setDisable(true);
        creerGrille();
        int n = Integer.parseInt(super.labelScore.getText()), h = Integer.parseInt(super.labelHScore.getText()); //met le meilleur score à jour si nécessaire
        if (n > h) {
            super.labelHScore.setText(super.labelScore.getText());
            super.hScore = n;
        }
        super.labelScore.setText("0"); //remise à zéro du score affiché
    }

    /**
     * Allows the A.I. to play
     * @param d The int who represents the direction
     * @param partieR The boolean
     */
    @Override
    public void jouer(int d, boolean partieR) {
        boolean b2 = grilleModele.lanceurDeplacerCases2584(-2 + (int) (Math.random() * ((2 - -2) + 1)));
        if (b2) { //si on déplace au moins une case
            super.jouerC();
        }
    }
}
