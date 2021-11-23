package interface2584;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable, jeu2584.Parametres {

    //Variables globales correspondant à des objets définis dans la vue (fichier .fxml) 
    //Ces variables sont ajoutées à la main et portent le même nom que les fx:ID dans Scene Builder
    @FXML
    private Label score, score2, hScore, hScore2;
    @FXML
    private GridPane grille, grille2;
    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre

    // variables globales non définies dans la vue (fichier .fxml)
    private Joueur j1, j2;
    private boolean partieT = true, partieR = false; //partieT : si la partie est terminée, pour bloquer les boutons/touches. partieR : si partie est en mode rapide

    private void affichageGameOver(String s, double n) { //place un pane et un label devant une grilleAffichage quand la partie est finie
        Pane p = new Pane();
        fond.getChildren().add(p);
        p.getStyleClass().add("pane");
        p.setLayoutX(24 * n);
        p.setLayoutY(191);
        p.setPrefHeight(397);
        p.setPrefWidth(397);
        p.setStyle("-fx-background-color: #fff9f1bf");

        Label l = new Label();
        l.setText(s);
        p.getChildren().add(l);
        l.setPrefWidth(397);
        l.setAlignment(Pos.CENTER);
        l.setLayoutY(170);
        l.setStyle("-fx-font-size: 15;");

        p.setVisible(true);
    }

    private void finPartie(boolean v) {
        if (v) {
            affichageGameOver(j1.grilleModele.victory2584(), 1);
            affichageGameOver(j2.grilleModele.gameOver2584(), 18.37);
        } else {
            affichageGameOver(j1.grilleModele.gameOver2584(), 1);
            affichageGameOver(j2.grilleModele.victory2584(), 18.37);
        }
        partieT = true;
    }

    private void nouvellePartie(boolean b) {
        if (fond.getChildren().size() > 16) { //supprime les panes semi-transparents de fin de partie, si ils existent
            fond.getChildren().remove(17);
            fond.getChildren().remove(16);
        }
        partieR = b;
        j1 = new Joueur(grille, score, hScore);
        j2 = new Joueur(j1.grilleModele, grille2, score2, hScore2);
        System.out.println(j1.grilleModele);
        System.out.println("Clic de souris sur le bouton menu");
        partieT = false; //met la booléenne partie terminée à faux
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("le contrôleur initialise la vue");
        fond.getStyleClass().add("pane");
        //GridPane.setHalignment(c, HPos.CENTER);
        //GridPane.setHalignment(c2, HPos.CENTER);
    }

    //Méthodes listeners pour gérer les événements (portent les mêmes noms dans le document .fxml)
    @FXML
    private void nouvellePartieChoix() { //ouvre une nouvelle fenètre pour régler les paramètres de la nouvelle partie
        Button btnNvPartie = new Button("Nouvelle Partie");
        CheckBox checkBoxRMode = new CheckBox("Mode Rapide");
        checkBoxRMode.setTooltip(new Tooltip("Pénalité de 5 secondes pendant lesquelles un joueur ne peut plus déplacer de cases s’il n’a effectué aucun déplacement pendant "
                + "10 secondes"));

        Pane fondNouvellePartie = new Pane();
        fondNouvellePartie.getChildren().add(checkBoxRMode);
        fondNouvellePartie.getChildren().add(btnNvPartie);
        fondNouvellePartie.setStyle("-fx-background-color: #fefaf1");

        Stage fenetreNouvellePartie = new Stage();
        fenetreNouvellePartie.initModality(Modality.WINDOW_MODAL); //bloque la fenètre parent de la nouvelle fenètre
        fenetreNouvellePartie.initOwner(fond.getScene().getWindow()); //désigne la fenètre principale comme la fenètre parent
        fenetreNouvellePartie.setTitle("Nouvelle Partie");
        Scene sceneNouvellePartie = new Scene(fondNouvellePartie, 250, 100);
        sceneNouvellePartie.getStylesheets().add("css/styles.css");
        fenetreNouvellePartie.setScene(sceneNouvellePartie);
        fenetreNouvellePartie.show();

        btnNvPartie.setOnAction(new EventHandler<ActionEvent>() { //Au clic sur le bouton "Nouvelle Partie" :
            @Override
            public void handle(ActionEvent event) {
                nouvellePartie(checkBoxRMode.isSelected()); //crée une nouvelle partie et passe en paramètre la valeur de la checkBox pour le mode rapide
                fenetreNouvellePartie.close(); //ferme la nouvelle fenètre
            }
        });

        checkBoxRMode.setLayoutY(sceneNouvellePartie.getHeight() / 3); //positionne le bouton et la checkBox dans la nouvelle fenètre
        checkBoxRMode.setLayoutX((sceneNouvellePartie.getWidth() / 2) - checkBoxRMode.getWidth() / 2);
        btnNvPartie.setLayoutY((sceneNouvellePartie.getHeight() / 3) * 2);
        btnNvPartie.setLayoutX((sceneNouvellePartie.getWidth() / 2) - btnNvPartie.getWidth() / 2);
    }

    @FXML
    private void keyPressed(KeyEvent ke) {
        System.out.println("touche appuyée");
        if (!partieT) {
            if (!j1.grilleModele.partieFinie2584() && !j2.grilleModele.partieFinie2584()) {
                String touche = ke.getText().toLowerCase();
                Boolean b = null;
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

                if (b != null) {
                    if (b) { //joueur 1 (grilleAffichage gauche)
                        j1.jouer(direction, partieR);
                    } else { //joueur 2 (grilleAffichage droite)
                        j2.jouer(direction, partieR);
                    }
                }
                if (j1.grilleModele.getValeurMax() >= OBJECTIF2584) {
                    finPartie(true);
                } else if (j2.grilleModele.getValeurMax() >= OBJECTIF2584) {
                    finPartie(false);
                }
            } else {
                if (j1.grilleModele.partieFinie2584()) {
                    finPartie(false);
                } else {
                    finPartie(true);
                }
            }
        }
    }

    @FXML
    private void fermer() {
        System.exit(0);
    }
}
