package jeu2584;

import java.util.HashSet;
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
            super.nbAnnuls--;
            super.buttonAnnuler.setDisable(true); //désactive le bouton pour annuler
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

    @Override
    public void jouer(int direction, boolean partieR) {
        if (partieR && (this.dernierEJ - System.currentTimeMillis()) > 0) { //si le mode rapide est activé et que la dernière entrée du joueur est dans le futur
            System.out.println("PUNITION en cours pour le joueur qui est LENT"); //ce qui ne peut arriver que si le joueur est pénalisé, on ne le laisse pas jouer
        } else {
            if (partieR && (System.currentTimeMillis() - this.dernierEJ) >= 10000) { //si la dernière entrée du joueur date d'il y a plus de 10 secondes
                this.dernierEJ = (System.currentTimeMillis() + 5000); //on place sa dernière entrée dans le futur en lui ajoutant 5 secondes
                System.out.println("Le joueur est PUNI car il est LENT");
            } else {
                if (this.grillePrec != null && this.getNbAnnuls() > 0) { //si l'ensemble (grilleAffichage) précédent n'est pas vide
                    this.buttonAnnuler.setDisable(false); //active le bouton pour annuler
                }
                this.dernierEJ = (System.currentTimeMillis());
                HashSet<Case> ensTemp = new HashSet<>(); //crée un ensemble de cases pour sauvegarder la grille avant de faire le déplacement
                int scorePrec = grilleModele.getScore(); //sauvegarde du score avant le déplacement
                grilleModele.copierGrille(ensTemp); //sauvegarde la grille pré-déplacement dans l'ensemble

                boolean b2 = grilleModele.lanceurDeplacerCases2584(direction);
                if (b2) { //si on déplace au moins une case
                    if (this.nbAnnuls > 0) { //si nombre d'annulations du joueur est >0 stocke l'ensemble temporaire dans l'ensemble (=la grille) de grillePrec
                        this.grillePrec.setGrille(ensTemp); // pour que le joueur puisse retourner en arrière si besoin
                        this.grillePrec.setScore(scorePrec); //sauvegarde aussi le score
                        buttonAnnuler.setDisable(false); //active le bouton pour annuler
                    }
                    super.jouerC();
                }
            }
        }
    }
}
