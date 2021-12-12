package outils;

import jeu2584.Joueur;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Parser {

    private final File fichier;

    /*
     * Le constructeur suppose qu'il y a dans le dossier Project2584-Melki-Najeh-Renard-Tepe de votre projet netbeans
     * un fichier dont le nom est passé en paramètre
     */
    public Parser(String nomfichier) {
        this.fichier = new File(nomfichier);
    }

    // Vérifie si l'attribut fichier (variable globale ci-dessus) est un fichier existant sur votre disque dur
    public boolean doesFileExists() {
        try {
            return this.fichier.exists();
        } catch (SecurityException se) {
            System.out.println("Security Exception : access to file denied");
            return false;
        }
    }

    public ArrayList<Joueur> chargerJoueurs() { //manière alternative pour charger les joueurs en utilisant la sérialisation
        try {
            FileInputStream fin = new FileInputStream(fichier);
            ObjectInputStream ois = new ObjectInputStream(fin);
            ArrayList<Joueur> lJ = new ArrayList<>();
            lJ.add((Joueur) ois.readObject());
            lJ.add((Joueur) ois.readObject());
            ois.close();
            return lJ;
        } catch (FileNotFoundException ex) {
            System.out.println("Le fichier " + this.fichier.getAbsolutePath() + " est un dossier ou ne peut pas être ouvert");
        } catch (IOException ioe) {
            System.out.println("Probleme pendant la lecture du fichier " + this.fichier.getAbsolutePath());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Probleme pendant la lecture du fichier " + this.fichier.getAbsolutePath());
        }
        return null;
    }

    public void sauvegarderJoueurs(Joueur j1, Joueur j2) {
        try {
            FileOutputStream fos = new FileOutputStream(this.fichier);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(j1);
            oos.writeObject(j2);
            oos.close();
        } catch (IOException ioe) {
            System.out.println("Probleme pendant l'écriture du fichier " + this.fichier.getAbsolutePath());
        }
    }
}
