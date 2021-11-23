package interface2584;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import jeu2584.Case;
import jeu2584.Grille;
import outils.Parser;

public class Joueur implements Serializable {

    public Grille grilleModele;
    private int hScore = 0, nbMouvements = 0;
    public Label labelScore, labelHScore;
    public GridPane grilleAffichage;
    private long dernierEJ;
    private ArrayList<Tuile> listeTuileJ = new ArrayList<>();
    private ParallelTransition parallelTransition = new ParallelTransition();

    public Joueur(GridPane g, Label s, Label hS) { //permet de construire un joueur avec une nouvelle grille
        this.grilleModele = new Grille();
        grilleModele.nouvelleCase2584GUI(); //crée une nouvelle case dans la grilleAffichage du joueur 1 (modèle)
        grilleModele.nouvelleCase2584GUI(); //crée une deuxième case dans la grilleAffichage du joueur 1 (modèle)
        communsJoueur(g, s, hS);
    }

    public Joueur(Grille gM, GridPane g, Label s, Label hS) { //permet de construire un joueur avec une grille identique à celle d'un autre
        this.grilleModele = new Grille(gM); //copie la grille
        communsJoueur(g, s, hS);
    }

    private void communsJoueur(GridPane g, Label s, Label hS) { //rassemble les éléments communs aux 2 constructeurs possibles pour joueur
        this.dernierEJ = System.currentTimeMillis();
        grilleAffichage = g;
        labelScore = s;
        labelHScore = hS;
        creerGrille();
        int n = Integer.parseInt(labelScore.getText()), h = Integer.parseInt(labelHScore.getText()); //met le meilleur score à jour si nécessaire
        if (n > h) {
            labelHScore.setText(labelScore.getText());
            this.hScore = n;
        }
        labelScore.setText("0"); //remise à zéro du score affiché
    }

    public long getDernierEJ() {
        return this.dernierEJ;
    }

    public int getHScore() {
        return this.hScore;
    }

    public int getNbMouvements() {
        return this.nbMouvements;
    }

    public void setDernierEJ(long l) {
        this.dernierEJ = l;
    }

    public void setHScore(int n) {
        this.hScore = n;
    }

    public void creerGrille() { //recrée une grille (affichage) à partir de la grille (modèle) passée en paramètre
        grilleAffichage.getChildren().clear(); //remise à zéro des grilles (affichage)
        listeTuileJ.clear();

        for (Case c : grilleModele.getGrille()) {
            this.creerCase(c);
        }
    }

    private void creerCase(Case c) { //ajoute une case dans la grille (affichage) du joueur
        StackPane p = new StackPane();
        p.getStyleClass().add("pane");
        p.setStyle("-fx-background-color: #" + c.detCouleur()); //fixe la couleur de la case grâce à detCouleur, qui renvoie un code HTML correspondant à la valeur de la case
        Label l = new Label();
        l.getStyleClass().add("tuile");
        grilleAffichage.add(p, c.getX(), c.getY()); //ajoute la case (affichage) dans la grilleAffichage (affichage) aux coordonnées correspondantes de la case (modèle)
        p.getChildren().add(l); //ajoute le label dans la case
        StackPane.setAlignment(l, Pos.CENTER); //aligne le label au centre de la case
        l.setText(String.valueOf(c.getValeur()));
        p.setVisible(true);

        listeTuileJ.add(new Tuile(c, p));

        l.setVisible(true);
    }

    public void jouer(int direction, boolean partieR) {
        if (partieR && (this.dernierEJ - System.currentTimeMillis()) > 0) { //si le mode rapide est activé et que la dernière entrée du joueur est dans le futur
            System.out.println("PUNITION en cours pour le joueur qui est LENT"); //ce qui ne peut arriver que si le joueur est pénalisé, on ne le laisse pas jouer
        } else {
            if (partieR && (System.currentTimeMillis() - this.dernierEJ) >= 10000) { //si la dernière entrée du joueur date d'il y a plus de 10 secondes
                this.dernierEJ = (System.currentTimeMillis() + 5000); //on place sa dernière entrée dans le futur en lui ajoutant 5 secondes
                System.out.println("Le joueur est PUNI car il est LENT");
            } else {
                this.dernierEJ = (System.currentTimeMillis());

                boolean b2 = grilleModele.lanceurDeplacerCases2584(direction);
                if (b2) { //si on déplace au moins une case
                    labelScore.setText(String.valueOf(grilleModele.getScore())); //met à jour le score
                    for (Tuile tL : listeTuileJ) { //pour chaque tuile (ensemble case (affichage) et case (modèle) contenue dans la liste du joueur
                        animate(tL.p, tL.xY[0], tL.xY[1], tL.c.getX(), tL.c.getY()); //génère et ajoute les animations à parallelTransition
                    }
                    parallelTransition.setOnFinished(e -> { //quand toutes les transitions sont finies
                        creerGrille();  //recrée une grilleAffichage (affichage) à partir de la grilleAffichage (modèle) passée en paramètre
                        Case c = grilleModele.nouvelleCase2584GUI(); //ajoute une nouvelle case à la grilleAffichage
                        this.creerCase(c);
                        System.out.println(grilleModele);
                    });
                    parallelTransition.play(); //joue les transitions
                    parallelTransition.getChildren().clear();
                    nbMouvements++;
                }
            }
        }
    }

    private void animate(Node t, int xOrigine, int yOrigine, int xNV, int yNV) { //https://stackoverflow.com/questions/59269930/is-there-a-way-to-animate-elements-in-gridpane
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setToX((xNV - xOrigine) * (grilleAffichage.getPrefHeight() / 4));
        translateTransition.setToY((yNV - yOrigine) * (grilleAffichage.getPrefWidth() / 4));
        translateTransition.setNode(t);

        translateTransition.setOnFinished(e -> { //quand l'animation est finie, on retire la case de sa position d'origine
            grilleAffichage.getChildren().remove(t); //on ajoute la case à sa nouvelle position et on met ses valeurs translate à 0
            t.setTranslateX(0);
            t.setTranslateY(0);
            grilleAffichage.add(t, xNV, yNV);
        });

        parallelTransition.getChildren().add(translateTransition);
    }
}
