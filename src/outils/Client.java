package outils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jeu2584.Grille;

public class Client {

    int port;
    String adress;

    public Client(int p, String a) {
        port = p;
        adress = a;
        try {
            Socket s = new Socket("localhost", port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Grille recuperationGrilleClient() {
        int port = 1135;
        System.out.println("Client lancé");
        Socket s = null;
        try {
            s = new Socket("localhost", port);
            InputStream in = s.getInputStream();
            //OutputStream out = s.getOutputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);
            try {
                //ObjectOutputStream objOut = new ObjectOutputStream(out);
                //System.out.println(g);
                Grille g = (Grille) objIn.readObject();
                return g;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            //objOut.writeObject(j);
            s.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static Grille envoiGrilleClient(Grille g) {
        int port = 1135;
        System.out.println("Client lancé");
        Socket s = null;
        try {
            s = new Socket("localhost", port);
            OutputStream out = s.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(g);
            s.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
