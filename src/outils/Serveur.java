package outils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import jeu2584.Grille;

public class Serveur {

    public static void envoiGrilleServeur(Grille g) {
        int port = 1135;
        System.out.println("Serveur lancé");
        try {
            ServerSocket ecoute = new ServerSocket(port);
            while (!ecoute.isClosed()) {
                Socket s = ecoute.accept();
                System.out.println(s.getInetAddress());
                //InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream();
                //ObjectInputStream objIn = new ObjectInputStream(in);
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                objOut.writeObject(g);
                //UnObjet o = (UnObjet) objIn.readObject();
                ecoute.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
