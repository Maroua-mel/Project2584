package interface2584;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import jeu2584.Case;
import jeu2584.Grille;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;

public class FXMLDocumentController implements Initializable, jeu2584.Parametres {

    //Variables globales correspondant à des objets définis dans la vue (fichier .fxml) 
    //Ces variables sont ajoutées à la main et portent le même nom que les fx:ID dans Scene Builder
    @FXML
    private Label score, score2, hScore, hScore2;
    @FXML
    private GridPane grille, grille2;
    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre

    // variables globales pour initialiser le modèle
    private Grille grilleModele, grilleModele2;

    // variables globales non définies dans la vue (fichier .fxml)
    private boolean partieT = true;

    private void creerCase(Case c, GridPane g) { //ajoute une case dans la grille passée en paramètre
        StackPane p = new StackPane();
        p.getStyleClass().add("pane");
        p.setStyle("-fx-background-color: #" + c.detCouleur()); //fixe la couleur de la case grâce à detCouleur, qui renvoie un code HTML correspondant à la valeur de la case
        Label l = new Label();
        l.getStyleClass().add("tuile");
        g.add(p, c.getX(), c.getY()); //ajoute la case (affichage) dans la grille (affichage) aux coordonnées correspondantes de la case (modèle)
        p.getChildren().add(l); //ajoute le label dans la case
        StackPane.setAlignment(l, Pos.CENTER); //aligne le label au centre de la case
        l.setText(String.valueOf(c.getValeur()));
        p.setVisible(true);
        l.setVisible(true);
    }

    private void affichageGameOver(String s, double n) { //place un pane et un label devant une grille quand la partie est finie
        Pane p = new Pane();
        fond.getChildren().add(p);
        p.getStyleClass().add("pane");
        p.setLayoutX(24 * n);
        p.setLayoutY(191);
        p.setPrefHeight(397);
        p.setPrefWidth(397);
        p.setStyle("-fx-background-color: #fff9f1bf");

        Label l = new Label();
        l.getStyleClass().add("tuile");
        l.setText(s);
        p.getChildren().add(l);
        l.setPrefWidth(397);
        l.setAlignment(Pos.CENTER);
        l.setLayoutY(170);
        l.setStyle("-fx-font-size: 15;");

        p.setVisible(true);
    }

    private void creerGrille(GridPane g, Grille gM) { //recrée une grille (affichage) à partir de la grille (modèle) passée en paramètre
        g.getChildren().clear(); //remise à zéro des grilles (affichage)
        for (Case c : gM.getGrille()) {
            this.creerCase(c, g);
        }
    }

    private void finPartie(boolean v) {
        if (v) {
            affichageGameOver(grilleModele.victory2584(), 1);
            affichageGameOver(grilleModele2.gameOver2584(), 18.37);
        } else {
            affichageGameOver(grilleModele.gameOver2584(), 1);
            affichageGameOver(grilleModele2.victory2584(), 18.37);
        }
        partieT = true;
    }

    private void jouer(int direction, GridPane g, Grille gM, Label s) {
        boolean b2 = gM.lanceurDeplacerCases2584(direction);
        if (b2) {
            s.setText(String.valueOf(gM.getScore()));
            creerGrille(g, gM);  //recrée une grille (affichage) à partir de la grille (modèle) passée en paramètre
            Case c = gM.nouvelleCase2584GUI(); //ajoute une nouvelle case à la grille
            this.creerCase(c, g);
            System.out.println(gM);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("le contrôleur initialise la vue");
        //GridPane.setHalignment(c, HPos.CENTER);
        //GridPane.setHalignment(c2, HPos.CENTER);
    }

    //Méthodes listeners pour gérer les événements (portent les mêmes noms dans le document .fxml)
    @FXML
    private void handleButtonAction(MouseEvent event) {
        System.out.println(fond.getChildren().size());
        if (fond.getChildren().size() > 10) { //supprime les panes semi-transparents de fin de partie, si ils existent
            fond.getChildren().remove(11);
            fond.getChildren().remove(10);
        }

        grilleModele = new Grille(); //remise à zéro des grilles (modele)
        Case c = grilleModele.nouvelleCase2584GUI(); //crée une nouvelle case dans la grille du joueur 1 (modèle)
        c = grilleModele.nouvelleCase2584GUI(); //crée une deuxième case dans la grille du joueur 1 (modèle)
        creerGrille(grille, grilleModele); //génère la grille (affichage) du joueur 1 à partir du modèle passé en paramètre
        grilleModele2 = new Grille(grilleModele); //copie la grille 1 (modele) et son contenu dans la grille 2 (modele)
        creerGrille(grille2, grilleModele2); //génère la grille (affichage) du joueur 2 à partir du modèle passé en paramètre
        System.out.println("Clic de souris sur le bouton menu");

        partieT = false; //met la booléenne partie terminée à faux

        int s = Integer.parseInt(score.getText()), h = Integer.parseInt(hScore.getText()); //met les meilleurs scores à jour si nécessaire
        if (s > h) {
            hScore.setText(score.getText());
            grilleModele.setHScore(s);
        }
        s = Integer.parseInt(score2.getText());
        h = Integer.parseInt(hScore2.getText());
        if (s > h) {
            hScore2.setText(score2.getText());
            grilleModele2.setHScore(s);
        }
        score.setText("0"); //remise à zéro des scores affichés
        score2.setText("0");
        System.out.println(grilleModele);
    }

    @FXML
    public void keyPressed(KeyEvent ke) {
        System.out.println("touche appuyée");
        if (!partieT) {
            if (!grilleModele.partieFinie2584() && !grilleModele2.partieFinie2584()) {
                String touche = ke.getText().toLowerCase();
                boolean b = true;
                int direction = 0;
                switch (touche) {
                    case "q":
                        direction = GAUCHE;
                        b = true;
                        break;
                    case "d":
                        direction = DROITE;
                        b = true;
                        break;
                    case "z":
                        direction = HAUT;
                        b = true;
                        break;
                    case "s":
                        direction = BAS;
                        b = true;
                        break;
                    case "k":
                        direction = GAUCHE;
                        b = false;
                        break;
                    case "m":
                        direction = DROITE;
                        b = false;
                        break;
                    case "o":
                        direction = HAUT;
                        b = false;
                        break;
                    case "l":
                        direction = BAS;
                        b = false;
                        break;
                }

                if (b) { //joueur 1 (grille gauche)
                    jouer(direction, grille, grilleModele, score);
                } else { //joueur 2 (grille droite)
                    jouer(direction, grille2, grilleModele2, score2);
                }
                if (grilleModele.getValeurMax() >= OBJECTIF2584) {
                    finPartie(true);
                } else if (grilleModele2.getValeurMax() >= OBJECTIF2584) {
                    finPartie(false);
                }
            } else {
                if (grilleModele.partieFinie2584()) {
                    finPartie(false);
                } else {
                    finPartie(true);
                }
            }
        }
    }
}
