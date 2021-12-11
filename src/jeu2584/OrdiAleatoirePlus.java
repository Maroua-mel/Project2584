package jeu2584;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OrdiAleatoirePlus extends Joueur implements Parametres { //choisit une direction aléatoire dans laquelle jouer, mais évite les directions qui ne déplaceraient pas de cases

    private ArrayList<Integer> listeDirections = new ArrayList<>(Arrays.asList(GAUCHE, BAS, HAUT, DROITE)); //liste des directions

    public OrdiAleatoirePlus(Grille gM, GridPane g, Label s, Label hS, Button b, int c) { //permet de construire un joueur avec une grille identique à celle d'un autre
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

    @Override
    public void jouer(int d, boolean partieR) {
        Collections.shuffle(listeDirections); //mélange la liste
        boolean b2 = false;
        int i = 0;
        for (i = 0; i < 4; i++) { //pour chaque élément de la liste
            Grille grilleTest = new Grille(grilleModele); //crée une grille de test
            b2 = grilleTest.lanceurDeplacerCases2584(listeDirections.get(i)); //enregistre si il y a eu un déplacement avec cette direction
            if (b2) { //si oui
                break; //sort de la boucle
            }
        }
        grilleModele.lanceurDeplacerCases2584(listeDirections.get(i));
        if (b2) { //si on déplace au moins une case
            super.jouerC();
        }
    }

}
