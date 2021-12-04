package jeu2584;

import interface2584.Tuile;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OrdiAleatoire extends Joueur {

    public OrdiAleatoire(Grille gM, GridPane g, Label s, Label hS, Button b, int c) { //permet de construire un joueur avec une grille identique à celle d'un autre
        this.grilleModele = new Grille(gM); //copie la grille
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
    public void jouer(int direction, boolean partieR) {
        boolean b2 = grilleModele.lanceurDeplacerCases2584(direction);
        if (b2) { //si on déplace au moins une case
            labelScore.setText(String.valueOf(grilleModele.getScore())); //met à jour le score
            for (Tuile tL : listeTuileJ) { //pour chaque tuile (ensemble case (affichage) et case (modèle) contenue dans la liste du joueur
                animate(tL.p, tL.xY[0], tL.xY[1], tL.c.getX(), tL.c.getY()); //génère et ajoute les animations à parallelTransition
            }
            parallelTransition.setOnFinished(e -> { //quand toutes les transitions sont finies
                grilleModele.nouvelleCase2584(); //ajoute une nouvelle case à la grilleAffichage
                creerGrille();  //recrée une grilleAffichage (affichage) à partir de la grilleAffichage (modèle) passée en paramètre
                System.out.println(grilleModele);
            });
            parallelTransition.play(); //joue les transitions
            parallelTransition.getChildren().clear();
            nbMouvements++;
        }
    }
}
