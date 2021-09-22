package interface2584;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
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
    private Pane fond; // panneau recouvrant toute la fenêtre

    // variable globale pour initialiser le modèle
    private Grille grilleModele = new Grille();

    // variables globales non définies dans la vue (fichier .fxml
    public void creerCase(Case c) {
        Pane p = new Pane();
        p.getStyleClass().add("pane");
        Label l = new Label();
        l.getStyleClass().add("tuile");
        grille.add(p, c.getX(), c.getY());
        p.getChildren().add(l);
        l.setText(String.valueOf(c.getValeur()));
        p.setVisible(true);
        l.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("le contrôleur initialise la vue");
        // utilisation de styles pour la grille et la tuile (voir styles.css)
        grille.getStyleClass().add("gridpane");
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
        grilleModele = new Grille();
        //https://stackoverflow.com/questions/11147788/clean-gridpane-in-javafx-and-maintain-the-grid-line
        Node n = grille.getChildren().get(0);
        grille.getChildren().clear();
        grille.getChildren().add(0, n);
        System.out.println("Clic de souris sur le bouton menu");
        Case c = grilleModele.nouvelleCase2584GUI();
        this.creerCase(c);
        c = grilleModele.nouvelleCase2584GUI();
        this.creerCase(c);
        System.out.println(grilleModele);
    }

    @FXML
    public void keyPressed(KeyEvent ke) {
        System.out.println("touche appuyée");
        String touche = ke.getText();
        int direction = 0;
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            direction = GAUCHE;
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            direction = DROITE;
        } else if (touche.compareTo("z") == 0) {
            direction = HAUT;
        } else if (touche.compareTo("s") == 0) {
            direction = BAS;
        }
        Boolean b2 = null;
        b2 = grilleModele.lanceurDeplacerCases2584(direction);
        Node n = grille.getChildren().get(0);
        grille.getChildren().clear();
        grille.getChildren().add(0, n);
        for (Case c : grilleModele.getGrille()) {
            this.creerCase(c);
        }
        System.out.println(b2);
        if (b2) {
            Case c = grilleModele.nouvelleCase2584GUI();
            System.out.println(grilleModele);
            if (c == null) {
                grilleModele.gameOver();
            }
            this.creerCase(c);
        }
    }
}
