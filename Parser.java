package outils;

import interface2584.Joueur;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import jeu2584.Case;
import jeu2584.Grille;

public class Parser {

    private final File fichier;

    /*
     * Le constructeur suppose qu'il y a dans le dossier Project2584-Melki-Najeh-Renard-Tepe de votre projet netbeans
     * un fichier dont le nom est passé en paramètre (ici sauvegarde.2584)
     */
    public Parser(String nomfichier) {
        this.fichier = new File(nomfichier);
    }

    // Vérifie si l'attribut fichier (variable globale ci-dessus) est un fichier existant sur votre disque dur
    private boolean doesFileExists() {
        try {
            return this.fichier.exists();
        } catch (SecurityException se) {
            System.out.println("Security Exception : access to file denied");
            return false;
        }
    }

    /*
     * Ajoute une ligne dans le fichier .2584 (voir attribut de classe) correspondant à une grilleAffichage
     * Méthodes utiles : write(String s), newLine() et close() dans la classe BufferedWriter
     */
    public void sauvegarderGrille(Joueur j, boolean b) {
        if (!this.doesFileExists()) {
            System.out.println("Le fichier n'existe pas : création d'un nouveau fichier à l'emplacement " + this.fichier.getAbsolutePath());
            try {
                FileOutputStream fileOutput = new FileOutputStream(fichier, false);
            } catch (FileNotFoundException ex) {
                System.out.println("Le fichier " + this.fichier.getAbsolutePath() + " est un dossier, n'a pas pu être créé ou ne peut pas être ouvert");
            }
        }
        ArrayList<String> l = new ArrayList<>();
        try {
            FileWriter fwriter = new FileWriter(this.fichier, b);
            BufferedWriter outputFile = new BufferedWriter(fwriter);
            outputFile.newLine();
            outputFile.write(String.valueOf(j.grilleModele.getScore())); //sauvegarde des scores du joueur et de son nombre d'annulation
            outputFile.write(" " + String.valueOf(j.getHScore()));
            outputFile.newLine();
            outputFile.write(j.grilleModele.toString()); //sauvegarde de la grilleAffichage en format texte
            outputFile.newLine();
            outputFile.write(String.valueOf(j.grillePrec.getScore())); //sauvegarde du score de la grilleAffichage précédente
            outputFile.newLine();
            outputFile.write(j.grillePrec.toString()); //sauvegarde de la grilleAffichage précédente en format texte
            outputFile.close();
            System.out.println("Joueur sauvegardée avec succès");
        } catch (IOException ioe) {
            System.out.println("Probleme pendant l'écriture du fichier " + this.fichier.getAbsolutePath());
            ioe.printStackTrace();
        }
    }

    /*
     * Parcourt le fichier et retourne la liste des grilles contenus dans le fichier .2584
     * Méthode utile : getFileContent() de cette classe Parser qui contient chaque ligne du fichier sous forme d'une String
     */
    public Grille parseGrille(int l) {
        ArrayList<String> lignes = this.getFileContent();
        if (lignes != null && lignes.size() > 1) {
            Grille g = new Grille();
            g.setScore(Integer.valueOf(lignes.get(l).split(" ")[0]));
            for (int j = 1 + l; j < 5 + l; j++) {
                String t = lignes.get(j).replaceAll("(\\[|\\,|\\])", ""); //retire les [, les ] et les ,
                for (int i = 0; i < 4; i++) {
                    int n = Integer.valueOf(t.split(" ")[i]);
                    if (n != 0) {
                        Case c = new Case(i, j - 1 - l, n);
                        g.getGrille().add(c);
                        c.setGrille(g);
                    }
                }
            }
            return g;
        } else {
            System.out.println("Pas de grilles dans le fichier");
            return null;
        }
    }

    public int[] parseInfoJoueur(int n) {
        ArrayList<String> lignes = this.getFileContent();
        if (lignes != null && lignes.size() > 1) {
            int[] tab = new int[2];
            tab[0] = Integer.valueOf(lignes.get(n).split(" ")[1]); //sauvegarde du nombre d'annulation
            tab[1] = Integer.valueOf(lignes.get(n).split(" ")[2]); //sauvegarde du meilleur score du joueur
            return tab;
        }
        System.out.println("Le fichier est vide");
        return null;
    }

    /*
     * Parcourt le fichier .2584 et retourne son contenu sous forme d'une liste de String (chaque ligne du fichier correspond à une String)
     * Méthode utile : readLine() et close() dans la classe BufferedReader
     */
    private ArrayList<String> getFileContent() {
        if (this.doesFileExists()) {
            ArrayList<String> l = new ArrayList<>();
            try {
                FileReader freader = new FileReader(this.fichier);
                BufferedReader inputFile = new BufferedReader(freader);
                String readLine = inputFile.readLine();
                while (readLine != null) {
                    l.add(readLine);
                    readLine = inputFile.readLine();
                }
                inputFile.close();
            } catch (IOException ioe) {
                System.out.println("Probleme pendant la lecture du fichier " + this.fichier.getAbsolutePath());
                ioe.printStackTrace();
            }
            return l;
        } else {
            System.out.println("Le fichier " + this.fichier.getAbsolutePath() + " n'existe pas");
            return null;
        }
    }
}
