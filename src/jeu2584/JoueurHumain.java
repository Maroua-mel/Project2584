package jeu2584;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class JoueurHumain extends Joueur {

    public JoueurHumain(GridPane g, Label s, Label hS, Button b, int c) { //permet de construire un joueur avec une nouvelle grille
        super.grilleModele = new Grille();
        super.grilleModele.nouvelleCase2584(); //crée une nouvelle case dans la grilleAffichage du joueur 1 (modèle)
        super.grilleModele.nouvelleCase2584(); //crée une deuxième case dans la grilleAffichage du joueur 1 (modèle)
        communsJoueur(g, s, hS, b, c);
    }

    public JoueurHumain(Grille gM, GridPane g, Label s, Label hS, Button b, int c) { //permet de construire un joueur avec une grille identique à celle d'un autre
        super.grilleModele = new Grille(gM); //copie la grille
        communsJoueur(g, s, hS, b, c);
    }

    private void communsJoueur(GridPane g, Label s, Label hS, Button b, int c) { //rassemble les éléments communs aux 2 constructeurs possibles pour joueur
        super.grillePrec = new Grille();
        super.dernierEJ = System.currentTimeMillis();
        super.grilleAffichage = g;
        super.style = c;
        super.labelScore = s;
        super.labelHScore = hS;
        super.buttonAnnuler = b;
        super.buttonAnnuler.setDisable(true);
        super.buttonAnnuler.setOnMouseClicked(e -> {
            super.grilleModele = new Grille(super.grillePrec); //écrase la grilleAffichage (modèle) actuelle avec la grilleAffichage (modèle) stockée
            creerGrille(); //re-crée la grilleAffichage (affichage) à partir de la nouvelle grilleAffichage (modèle)
            System.out.println(super.grilleModele);
            super.nbAnnuls--;
            super.buttonAnnuler.setDisable(true); //réactive le bouton pour annuler
            super.labelScore.setText(String.valueOf(super.grilleModele.getScore())); //met à jour le labelScore (affiché) du joueur
        });
        creerGrille();
        int n = Integer.parseInt(super.labelScore.getText()), h = Integer.parseInt(super.labelHScore.getText()); //met le meilleur score à jour si nécessaire
        if (n > h) {
            super.labelHScore.setText(super.labelScore.getText());
            super.hScore = n;
        }
        super.labelScore.setText("0"); //remise à zéro du score affiché
    }
}
