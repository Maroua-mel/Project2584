package jeu2584;

import interface2584.Tuile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public abstract class Joueur implements Serializable {

    protected Grille grilleModele, grillePrec;
    protected int nbAnnuls = 5, hScore = 0, nbMouvements = 0, style;
    public transient Label labelScore, labelHScore;
    public transient GridPane grilleAffichage;
    public transient Button buttonAnnuler;
    protected long dernierEJ;
    protected transient ArrayList<Tuile> listeTuileJ = new ArrayList<>();
    protected transient ParallelTransition parallelTransition = new ParallelTransition();

    public int getNbAnnuls() {
        return this.nbAnnuls;
    }

    public long getDernierEJ() {
        return this.dernierEJ;
    }

    public int getHScore() {
        return this.hScore;
    }

    public int getStyle() {
        return this.style;
    }

    public int getNbMouvements() {
        return this.nbMouvements;
    }

    public Grille getGrilleModele() {
        return this.grilleModele;
    }

    public Grille getGrillePrec() {
        return this.grillePrec;
    }

    public void setDernierEJ(long l) {
        this.dernierEJ = l;
    }

    public void setNbAnnuls(int n) {
        this.nbAnnuls = n;
    }

    public void setHScore(int n) {
        this.hScore = n;
    }

    public void setStyle(int b) {
        this.style = b;
        this.creerGrille();
    }

    public void creerGrille() { //recrée une grille (affichage) à partir de la grille (modèle) passée en paramètre
        grilleAffichage.getChildren().clear(); //remise à zéro des grilles (affichage)
        if (listeTuileJ != null) {
            listeTuileJ.clear();
        }

        for (Case c : grilleModele.getGrille()) {
            this.creerCase(c);
        }
    }

    protected void creerCase(Case c) { //ajoute une case dans la grille (affichage) du joueur
        StackPane p = new StackPane();
        p.getStyleClass().add("pane");
        p.setStyle("-fx-background-color: #" + c.detCouleur(this.style)); //detCouleur renvoie un code HTML correspondant à la valeur de la case
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
                if (this.grillePrec != null && this.getNbAnnuls() > 0) { //si l'ensemble (grilleAffichage) précédent n'est pas vide
                    this.buttonAnnuler.setDisable(false); //active le bouton pour buttonAnnuler
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
    }

    protected void animate(Node t, int xOrigine, int yOrigine, int xNV, int yNV) { //https://stackoverflow.com/questions/59269930/is-there-a-way-to-animate-elements-in-gridpane
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

    public void chargerJoueur(Joueur j) {
        this.grilleModele = j.grilleModele;
        this.nbAnnuls = j.nbAnnuls;
        this.style = j.style;
        if (j.grillePrec != null) { //si il existe une grille précédente, l'affecte
            this.grillePrec = j.grillePrec;
            if (this.nbAnnuls > 0) { //si le nombre d'annulations est supérieur à 5, active le bouton pour annuler
                this.buttonAnnuler.setDisable(false);
            }
        }
        this.hScore = j.hScore;
        this.nbMouvements = j.nbMouvements;
        int h = Integer.parseInt(labelHScore.getText()); //met le label meilleur score à jour si nécessaire
        if (this.hScore > h) { //le mettre à jour quoi qu'il arrive plutôt ?
            labelHScore.setText(String.valueOf(this.hScore));
        }
        this.creerGrille();
    }
}
