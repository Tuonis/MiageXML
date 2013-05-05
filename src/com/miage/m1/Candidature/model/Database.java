package com.miage.m1.Candidature.model;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * Cette classe permet de récupérer une connection à la base de données selon le
 * driver precise
 */
public class Database {

//  protected static final String DRIVER_NAME = "org.postgresql.Driver";
//  protected static final String URL = "jdbc:postgresql://localhost:5432/boutique";
    protected static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    protected static final String URL = "jdbc:mysql://localhost/xml";
    protected static final String USER = "root";
    protected static final String PASSWORD = "";

    public enum SortOrder {

        ASC, DESC;
    }

    static {
        // Chargement du pilote
        // Ne doit avoir lieu qu'une seule fois
        try {
            Class.forName(DRIVER_NAME).newInstance();
            System.out.println("*** Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("*** ERROR: Driver " + DRIVER_NAME + " not found");
        } catch (InstantiationException e) {
            System.err.println("*** ERROR: Impossible to create an instance of " + DRIVER_NAME);
            System.err.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("*** ERROR: Impossible to create an instance of " + DRIVER_NAME);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Fournit une connexion � la base de donn�es. Ne fait pas appel � un pool
     * de connexion, m�me si cela est envisageable plus tard (ne changerait rien
     * � l'appel de la m�thode)
     *
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Ex�cuter un fichier SQL sur la base de donn�es. Les instructions SQL y
     * sont toutes termin�es par un point virgule. Les lignes vides et les
     * espaces apr�s les points virgules sont ignor�s.
     *
     * @param path chemin du fichier SQL
     * @throws SQLException
     * @throws IOException
     */
    public static void loadSQL(String path) throws SQLException, IOException {
        // Analyseur de fichier
        Scanner scanner = null;
        Connection connexion = null;
        Statement ordre = null;
        try {
            scanner = new Scanner(new File(path));
            // Le delimiteur de blocs sera le point virgule suivi de 0 a n espaces
            scanner.useDelimiter(";\\s*");
            connexion = getConnection();
            ordre = connexion.createStatement();
            // On boucle sur chaque bloc detect�
            while (scanner.hasNext()) {
                // Recuperer l'instruction SQL
                String sql = scanner.next();
                System.out.println(sql);
                ordre.executeUpdate(sql);
            }
        } catch (SQLException exc) {
            throw exc;
        } catch (IOException exc) {
            throw exc;
        } finally {
            if (ordre != null) {
                ordre.close();
            }
            if (connexion != null) {
                connexion.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Ferme une connexion, si elle est non null. Si une exception SQLException
     * est lev�e, ne la propage pas.
     */
    public static void close(final Connection cx) throws SQLException {
        if (cx != null) {
            try {
                cx.close();
            } catch (SQLException exc) {
                System.err.println("Impossible to close connection");
                System.err.println(exc.getMessage());
            }
        }
    }

    /**
     * Ferme une requ�te SQL, si elle est non null. Si une exception
     * SQLException est lev�e, ne la propage pas.
     */
    public static void close(final Statement st) throws SQLException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException exc) {
                System.err.println("Impossible to close statement");
                System.err.println(exc.getMessage());
            }
        }
    }

    /**
     * Ferme un ResultSet, s'il est non null. Si une exception SQLException est
     * lev�e, ne la propage pas.
     */
    public static void close(final ResultSet rs) throws SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException exc) {
                System.err.println("Impossible to close resultSet");
                System.err.println(exc.getMessage());
            }
        }
    }
}
