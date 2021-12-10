package outils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Classe utilisée pour établir une connexion avec la base de données, interroger la base et insérer de nouveaux tuples dans la base
public class ConnexionBDD implements Runnable {

    private final String host = "mysql-g11-202122-inglog.alwaysdata.net", port = "3306", dbname = "g11-202122-inglog_db2584", username = "249349", password = "2XujDyfxf2K8Myn";
    private Connection con = null;
    private String requete;

    /*
     * Ouvre la connexion avec la base de données
     */
    public ConnexionBDD() {

    }

    public void setRequete(String s) {
        requete = s;
    }

    @Override
    public void run() {
        this.insertTuples();
    }

    private void openConnexion() {
        String connectUrl = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbname;
        if (this.con != null) {
            this.closeConnexion();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            this.con = DriverManager.getConnection(connectUrl, this.username, this.password);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Cannot load db driver: com.mysql.jdbc.Driver");
            cnfe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erreur inattendue");
            e.printStackTrace();
        }
    }

    /*
     * Ferme la connexion avec la base de données
     */
    private void closeConnexion() {
        if (this.con != null) {
            try {
                this.con.close();
                System.out.println("Database connection terminated.");
            } catch (Exception e) {
                /* ignore close errors */ }
        }
    }

    /*
     * Interroge la base de données avec la requête passée en paramètre
     * et retourne les résultats sous forme d'une liste de String.
     * Il faut utiliser la méthode executeQuery dans la classe Statement (voir cours 12).
     */
    public ObservableList<Map<Integer, String>> getTuples(String query) {
        ObservableList<Map<Integer, String>> resultats
                = FXCollections.<Map<Integer, String>>observableArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbname, this.username, this.password);
            System.out.println("Database connection established.");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metadata = rs.getMetaData();
            while (rs.next()) { //pour chaque ligne résultat récupérée
                Map<Integer, String> resultatsLigne = new HashMap<>(); //crée une nouvelle map
                for (int i = 1; i <= metadata.getColumnCount(); i++) { //pour chaque élément dans la ligne résultat
                    resultatsLigne.put(i - 1, rs.getString(i)); //ajoute l'élément à la map avec un id
                }
                resultats.add(resultatsLigne); //ajoute la map à la liste
            }
        } catch (Exception e) {
            System.out.println("erreur");
        } finally {
            if (con != null) {
                try {
                    con.close();
                    System.out.println("Database connection terminated.");
                } catch (Exception e) {
                }
            }
        }
        return resultats;
    }

    /*
     * Insère un ou plusieurs tuples dans la base à partir de la requête passée en paramètre
     * Pour cela, il faut utiliser la méthode executeUpdate dans la classe Statement
     */
    public void insertTuples() {
        try {
            String updateQuery = "INSERT INTO resultats VALUES (" + requete + ");";
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbname, this.username, this.password);
            System.out.println("Database connection established.");
            Statement stmt = con.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (Exception e) {
            System.out.println("erreur");
        } finally {
            if (con != null) {
                try {
                    con.close();
                    System.out.println("Database connection terminated.");
                } catch (Exception e) {
                }
            }
        }
    }
}
