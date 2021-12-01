package interface2584;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jeu2584.Grille;
import outils.Client;
import outils.ConnexionBDD;
import outils.Parser;

public class FXMLDocumentController implements Initializable, jeu2584.Parametres {

    //Variables globales correspondant à des objets définis dans la vue (fichier .fxml) 
    //Ces variables sont ajoutées à la main et portent le même nom que les fx:ID dans Scene Builder
    @FXML
    private Label score, score2, hScore, hScore2;
    @FXML
    private GridPane grille, grille2;
    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre
    @FXML
    private Button annuler, annuler2;
    @FXML
    private MenuItem sauvegarder;

    // variables globales non définies dans la vue (fichier .fxml)
    private long dureePartie;
    private Joueur j1, j2;
    private boolean partieT = true, partieR = false, partieM; //partieT : si la partie est terminée, pour bloquer les boutons/touches. partieR : si partie est en mode rapide
    private int couleur = 0;
    private ArrayList<String> listeStyle;

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
        j1.buttonAnnuler.setDisable(true); //désactive les boutons pour annuler
        j2.buttonAnnuler.setDisable(true);
        sauvegarder.setDisable(true); //désactive le bouton pour sauvegarder

        //score, tuile maximum atteinte, nombre de déplacements effectués de chaque joueur et durée de la partie
        ConnexionBDD c = new ConnexionBDD();
        String query = j1.grilleModele.getScore() + ", " + j2.grilleModele.getScore() + ", " + j1.grilleModele.getValeurMax() + ", " + j2.grilleModele.getValeurMax()
                + ", " + j1.getNbMouvements() + ", " + j2.getNbMouvements() + ", " + Math.round((System.currentTimeMillis() - dureePartie) / 60000);
        c.insertTuples(query);
    }

    private void nouvellePartie(boolean b) {
        if (fond.getChildren().size() > 13) { //supprime les panes semi-transparents de fin de partie, si ils existent
            fond.getChildren().remove(14);
            fond.getChildren().remove(13);
        }
        partieR = b;
        j1 = new Joueur(grille, score, hScore, annuler, couleur);
        j2 = new Joueur(j1.grilleModele, grille2, score2, hScore2, annuler2, couleur);
        //Serveur.envoiGrilleServeur(j1.grilleModele);
        System.out.println(j1.grilleModele);
        System.out.println("Clic de souris sur le bouton menu");
        partieT = false; //met la booléenne partie terminée à faux
        dureePartie = System.currentTimeMillis();
    }

    private void changementStyle(int n) {
        if (n == listeStyle.size()) {

        } else {
            fond.getScene().getStylesheets().clear();
            fond.getScene().getStylesheets().add(listeStyle.get(n));
            couleur = n;
            if (!partieT) { //si une partie est en cours on ajuste la couleur des tuiles
                j1.setStyle(n);
                j2.setStyle(n);
            }
        }
    }

    private void nouveauStyle() {
        Button btnFermerNVStyle = new Button("Fermer");

        Pane fondNouveauStyle = new Pane();
        fondNouveauStyle.getChildren().add(btnFermerNVStyle);
        fondNouveauStyle.getStyleClass().add("fd");

        Stage fenetreNouveauStyle = new Stage();
        fenetreNouveauStyle.initModality(Modality.WINDOW_MODAL); //bloque la fenètre parent de la nouvelle fenètre
        fenetreNouveauStyle.initOwner(fond.getScene().getWindow()); //désigne la fenètre principale comme la fenètre parent
        fenetreNouveauStyle.setTitle("Modifier l'interface");
        Scene sceneNouveauStyle = new Scene(fondNouveauStyle, 250, 100);
        sceneNouveauStyle.getStylesheets().add(fond.getScene().getStylesheets().get(0));
        fenetreNouveauStyle.setScene(sceneNouveauStyle);
        fenetreNouveauStyle.show();
    }

    @FXML
    private void modifierInterface() {
        Button btnFermerStyle = new Button("Fermer");
        ChoiceBox choiceBoxStyle = new ChoiceBox();
        choiceBoxStyle.getItems().addAll("Défaut", "Style 1", "Style 2", "Ajouter un nouveau style");
        choiceBoxStyle.setValue(choiceBoxStyle.getItems().get(couleur));
        choiceBoxStyle.setOnAction((e) -> {
            changementStyle(choiceBoxStyle.getSelectionModel().getSelectedIndex());
        });

        Pane fondModifierInterface = new Pane();
        fondModifierInterface.getChildren().add(btnFermerStyle);
        fondModifierInterface.getChildren().add(choiceBoxStyle);
        fondModifierInterface.getStyleClass().add("fd");

        Stage fenetreModifierInterface = new Stage();
        fenetreModifierInterface.initModality(Modality.WINDOW_MODAL); //bloque la fenètre parent de la nouvelle fenètre
        fenetreModifierInterface.initOwner(fond.getScene().getWindow()); //désigne la fenètre principale comme la fenètre parent
        fenetreModifierInterface.setTitle("Modifier l'interface");
        Scene sceneModifierInterface = new Scene(fondModifierInterface, 250, 100);
        sceneModifierInterface.getStylesheets().add(fond.getScene().getStylesheets().get(0));
        fenetreModifierInterface.setScene(sceneModifierInterface);
        fenetreModifierInterface.show();

        btnFermerStyle.setOnAction(new EventHandler<ActionEvent>() { //Au clic sur le bouton "Fermer" :
            @Override
            public void handle(ActionEvent event) {
                fenetreModifierInterface.close(); //ferme la nouvelle fenètre
            }
        });

        choiceBoxStyle.setOnAction((e) -> {
            changementStyle(choiceBoxStyle.getSelectionModel().getSelectedIndex());
            fenetreModifierInterface.getScene().getStylesheets().clear();
            fenetreModifierInterface.getScene().getStylesheets().add(listeStyle.get(choiceBoxStyle.getSelectionModel().getSelectedIndex()));
        });
        double x = sceneModifierInterface.getHeight() - btnFermerStyle.getHeight();
        choiceBoxStyle.setPrefHeight(x); //change la taille de la table
        btnFermerStyle.setLayoutY(x); //positionne le bouton en bas de la nouvelle fenètre
        btnFermerStyle.setLayoutX((sceneModifierInterface.getWidth() / 2) - btnFermerStyle.getWidth() / 2); //positionne le bouton au centre de la nouvelle fenêtre
    }

    @FXML
    private void nouvellePartieClient() {
        Grille g = Client.recuperationGrilleClient();
        j1 = new Joueur(g, grille, score, hScore, annuler, couleur);
        j2 = new Joueur(j1.grilleModele, grille2, score2, hScore2, annuler2, couleur);
        System.out.println("Clic de souris sur le bouton menu");
        partieT = false; //met la booléenne partie terminée à faux
        dureePartie = System.currentTimeMillis();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("le contrôleur initialise la vue");
        fond.getStyleClass().add("pane");
        listeStyle = new ArrayList<>();
        listeStyle.add("css/stylesDefaut.css");
        listeStyle.add("css/stylesAltF4.css");
        listeStyle.add("css/stylesAlt.css");

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
        fondNouvellePartie.getStyleClass().add("fd");

        Stage fenetreNouvellePartie = new Stage();
        fenetreNouvellePartie.initModality(Modality.WINDOW_MODAL); //bloque la fenètre parent de la nouvelle fenètre
        fenetreNouvellePartie.initOwner(fond.getScene().getWindow()); //désigne la fenètre principale comme la fenètre parent
        fenetreNouvellePartie.setTitle("Nouvelle Partie");
        Scene sceneNouvellePartie = new Scene(fondNouvellePartie, 250, 100);
        sceneNouvellePartie.getStylesheets().add(fond.getScene().getStylesheets().get(0));
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
        sauvegarder.setDisable(false); //réactive la touche pour sauvegarder 
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

    @FXML
    private void enregistrer() throws FileNotFoundException, IOException {
        Parser p = new Parser("sauvegarde.2584");
        p.sauvegarderGrille(j1, false); //sauvegarde en effacant les données précédentes
        p.sauvegarderGrille(j2, true); //sauvegarde en conservant les données présentes dans le fichier
    }

    @FXML
    private void charger() {
        nouvellePartie(false); //pas bien bouh c'est nul
        j1.chargerJoueur(1);
        j2.chargerJoueur(13);
    }

    @FXML
    private void resultats() { //récupère les données de la BDD et les affiche dans une table, dans une nouvelle fenêtre
        ConnexionBDD c = new ConnexionBDD(); //http://tutorials.jenkov.com/javafx/tableview.html#using-maps-as-data-items
        ObservableList<Map<Integer, String>> oLResultats
                = c.getTuples("SELECT scoreJoueur1, scoreJoueur2, tuileMaxJoueur1, tuileMaxJoueur2, nombreDeplacementJoueur1, nombreDeplacementJoueur2, dureePartie FROM resultats LIMIT 10;");
        //récupération des données
        Button btnFermerResultats = new Button("Fermer"); //création du bouton pour fermer la fenêtre
        TableView tableViewResultats = new TableView(); //création de la table

        TableColumn<Map, String> tableColumnScoreJ1 = new TableColumn<>("Score J1"); //création de la colonne Score J1
        tableColumnScoreJ1.setCellValueFactory(new MapValueFactory<>(0)); //attribue les valeurs avec l'id 0 à cette colonne 
        tableViewResultats.getColumns().add(tableColumnScoreJ1); //ajoute la colonne à la table

        TableColumn<Map, String> tableColumnScoreJ2 = new TableColumn<>("Score J2"); //création de la colonne Score J2
        tableColumnScoreJ2.setCellValueFactory(new MapValueFactory<>(1));
        tableViewResultats.getColumns().add(tableColumnScoreJ2);

        TableColumn<Map, String> tableColumnTuileMaxJ1 = new TableColumn<>("Tuile Max J1"); //création de la colonne Tuile Max J1
        tableColumnTuileMaxJ1.setCellValueFactory(new MapValueFactory<>(2));
        tableViewResultats.getColumns().add(tableColumnTuileMaxJ1);

        TableColumn<Map, String> tableColumnTuileMaxJ2 = new TableColumn<>("Tuile Max J2");//création de la colonne Tuile Max J2
        tableColumnTuileMaxJ2.setCellValueFactory(new MapValueFactory<>(3));
        tableViewResultats.getColumns().add(tableColumnTuileMaxJ2);

        TableColumn<Map, String> tableColumnNbDéplacementsJ1 = new TableColumn<>("Déplacements J1"); //création de la colonne Déplacements J1
        tableColumnNbDéplacementsJ1.setCellValueFactory(new MapValueFactory<>(4));
        tableViewResultats.getColumns().add(tableColumnNbDéplacementsJ1);

        TableColumn<Map, String> tableColumnNbDéplacementsJ2 = new TableColumn<>("Déplacements J2"); //création de la colonne Déplacements J2
        tableColumnNbDéplacementsJ2.setCellValueFactory(new MapValueFactory<>(5));
        tableViewResultats.getColumns().add(tableColumnNbDéplacementsJ2);

        TableColumn<Map, String> tableColumnDuréePartie = new TableColumn<>("Durée Partie"); //création de la colonne Durée Partie
        tableColumnDuréePartie.setCellValueFactory(new MapValueFactory<>(6));
        tableViewResultats.getColumns().add(tableColumnDuréePartie);

        tableViewResultats.getItems().addAll(oLResultats); //ajoute la liste de résultats récupérés à la table

        Pane fondResultats = new Pane(); //crée le fond de la nouvelle fenètre
        fondResultats.getChildren().add(tableViewResultats); //ajoute la table au fond
        fondResultats.getChildren().add(btnFermerResultats); //ajoute le bouton au fond
        fondResultats.getStyleClass().add("fd");

        tableViewResultats.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Stage fenetreResultats = new Stage(); //crée une nouveau fenêtre
        fenetreResultats.initModality(Modality.WINDOW_MODAL); //bloque la fenètre parent de la nouvelle fenètre
        fenetreResultats.initOwner(fond.getScene().getWindow()); //désigne la fenètre principale comme la fenètre parent
        fenetreResultats.setTitle("Resultats"); //change le titre de la fenêtre
        Scene sceneResultats = new Scene(fondResultats);
        sceneResultats.getStylesheets().add(fond.getScene().getStylesheets().get(0));
        fenetreResultats.setScene(sceneResultats);
        fenetreResultats.show();

        btnFermerResultats.setOnAction(new EventHandler<ActionEvent>() { //Au clic sur le bouton "Fermer" :
            @Override
            public void handle(ActionEvent event) {
                fenetreResultats.close(); //ferme la nouvelle fenètre
            }
        });
        double x = sceneResultats.getHeight() - btnFermerResultats.getHeight();
        tableViewResultats.setPrefHeight(x); //change la taille de la table
        btnFermerResultats.setLayoutY(x); //positionne le bouton en bas de la nouvelle fenètre
        btnFermerResultats.setLayoutX((sceneResultats.getWidth() / 2) - btnFermerResultats.getWidth() / 2); //positionne le bouton au centre de la nouvelle fenêtre
    }
}
