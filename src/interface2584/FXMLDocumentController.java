package interface2584;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jeu2584.Case;
import jeu2584.Grille;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;

public class FXMLDocumentController implements Initializable, jeu2584.Parametres {

    /*
     * Variables globales correspondant à des objets définis dans la vue (fichier .fxml)
     * Ces variables sont ajoutées à la main et portent le même nom que les fx:id dans Scene Builder
     */
    @FXML
    private Label score;
    @FXML
    private GridPane grille;
    @FXML
    private Label score2;
    @FXML
    private GridPane grille2;
    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre

    // variable globale pour initialiser le modèle
    private Grille grilleModele;
    private Grille grilleModele2;

    // variables globales non définies dans la vue (fichier .fxml)
    private boolean partieT = true;

    private void creerCase(Case c, boolean b) { //ajoute une case dans la grille définie par la boolean
        Pane p = new Pane();
        p.getStyleClass().add("pane");
        p.setStyle("-fx-background-color: #" + c.detCouleur());
        Label l = new Label();
        l.getStyleClass().add("tuile");
        if (b) {
            grille.add(p, c.getX(), c.getY()); //le label et la case sont séparément liées à la grille pour que le label soit centré
            grille.add(l, c.getX(), c.getY());
        } else {
            grille2.add(p, c.getX(), c.getY()); //le label et la case sont séparément liées à la grille pour que le label soit centré
            grille2.add(l, c.getX(), c.getY());
        }
        GridPane.setHalignment(l, HPos.CENTER);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("le contrôleur initialise la vue");
        grille.getStyleClass().add("gridpane");
        grille2.getStyleClass().add("gridpane");
        fond.getStyleClass().add("fond");
        //GridPane.setHalignment(c, HPos.CENTER);
        //GridPane.setHalignment(c2, HPos.CENTER);
    }

    /*
     * Méthodes listeners pour gérer les événements (portent les mêmes noms que
     * dans Scene Builder
     */
    @FXML
    private void handleDragAction(MouseEvent event) {
        System.out.println("Glisser/déposer sur la grille avec la souris");
        double x = event.getX();//translation en abscisse
        double y = event.getY();//translation en ordonnée
        if (x > y) {
            for (int i = 0; i < grille.getChildren().size(); i++) { //pour chaque colonne
                //for (int j = 0; j < grille.getRowConstraints().size(); j++) { //pour chaque ligne
                System.out.println("ok1");
                grille.getChildren().remove(i);

                /*Node tuile = grille.getChildren().get(i);
                 if (tuile != null) {
                 int rowIndex = GridPane.getRowIndex(tuile);
                 int rowEnd = GridPane.getRowIndex(tuile);
                 }*/
                // }
            }
        } else if (x < y) {
            System.out.println("ok2");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Pane p = new Pane();
                    p.getStyleClass().add("pane");
                    grille.add(p, i, j);
                    p.setVisible(true);
                    grille.getStyleClass().add("gridpane");
                }
            }
        }
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (fond.getChildren().size() > 5) {
            fond.getChildren().remove(6);
            fond.getChildren().remove(5);
        }
        grilleModele = new Grille(); //remise à zéro des grilles (modele)
        //https://stackoverflow.com/questions/11147788/clean-gridpane-in-javafx-and-maintain-the-grid-line
        Node n = grille.getChildren().get(0);
        grille.getChildren().clear(); //remise à zéro des grilles (affichage)
        grille.getChildren().add(0, n);
        n = grille2.getChildren().get(0);
        grille2.getChildren().clear();
        grille2.getChildren().add(0, n);
        System.out.println("Clic de souris sur le bouton menu");
        Case c = grilleModele.nouvelleCase2584GUI(); //crée une nouvelle case dans la grille du joueur 1 (modèle)
        this.creerCase(c, true); //place la case dans la grille du joueur 1 (affichage)
        c = grilleModele.nouvelleCase2584GUI(); //crée une deuxième case dans la grille du joueur 1 (modèle)
        this.creerCase(c, true); //place la 2ème case dans la grille du joueur 1 (affichage)
        grilleModele2 = new Grille(grilleModele); //copie la grille 1 (modele) et son contenu dans la grille 2 (modele)
        for (Case c2 : grilleModele2.getGrille()) { //crée la grille du joueur 2 (affichage)
            this.creerCase(c2, false);
        }
        partieT = false;
        System.out.println(grilleModele);
    }

    @FXML
    public void keyPressed(KeyEvent ke) { //pourrait ajouter eventListener touches après initialize ???
        System.out.println("touche appuyée");
        if (!partieT) {
            if (!grilleModele.partieFinie2584() && !grilleModele2.partieFinie2584()) {
                String touche = ke.getText().toLowerCase();
                boolean b = true;
                int direction = 0;
                if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
                    direction = GAUCHE;
                    b = true;
                } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
                    direction = DROITE;
                    b = true;
                } else if (touche.compareTo("z") == 0) {
                    direction = HAUT;
                    b = true;
                } else if (touche.compareTo("s") == 0) {
                    direction = BAS;
                    b = true;
                } else if (touche.compareTo("k") == 0) {
                    direction = GAUCHE;
                    b = false;
                } else if (touche.compareTo("m") == 0) {
                    direction = DROITE;
                    b = false;
                } else if (touche.compareTo("o") == 0) {
                    direction = HAUT;
                    b = false;
                } else if (touche.compareTo("l") == 0) {
                    direction = BAS;
                    b = false;
                }

                Boolean b2 = null;
                if (b) { //joueur 1 (grille gauche)
                    b2 = grilleModele.lanceurDeplacerCases2584(direction);
                    score.setText(String.valueOf(grilleModele.getScore()));
                    Node n = grille.getChildren().get(0);
                    grille.getChildren().clear();
                    grille.getChildren().add(0, n);
                    for (Case c : grilleModele.getGrille()) {
                        this.creerCase(c, true);
                    }
                    if (b2) {
                        Case c = grilleModele.nouvelleCase2584GUI();
                        System.out.println(grilleModele);
                        this.creerCase(c, true);
                    }
                } else {
                    b2 = grilleModele2.lanceurDeplacerCases2584(direction);
                    score2.setText(String.valueOf(grilleModele2.getScore()));
                    Node n = grille2.getChildren().get(0);
                    grille2.getChildren().clear();
                    grille2.getChildren().add(0, n);
                    for (Case c : grilleModele2.getGrille()) {
                        this.creerCase(c, false);
                    }
                    if (b2) {
                        Case c = grilleModele2.nouvelleCase2584GUI();
                        System.out.println(grilleModele2);
                        this.creerCase(c, false);
                    }
                }
                if (grilleModele.getValeurMax() >= OBJECTIF2584) {
                    affichageGameOver(grilleModele.victory2584(), 1);
                    affichageGameOver("Perdu :)", 18.37);
                    partieT = true;
                } else if (grilleModele2.getValeurMax() >= OBJECTIF2584) {
                    affichageGameOver("Perdu :)", 1);
                    affichageGameOver(grilleModele2.victory2584(), 18.37);
                    partieT = true;
                }
            } else {
                affichageGameOver(grilleModele.gameOver2584(), 1);
                affichageGameOver(grilleModele2.gameOver2584(), 18.37);
                partieT = true;
            }
        }
    }
}
