/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuonis Home
 */
public class Etat {
    
    
    private int id;
    private String etat;

    public Etat(int id, String etat) {
        this.id = id;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + (this.etat != null ? this.etat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etat other = (Etat) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.etat == null) ? (other.etat != null) : !this.etat.equals(other.etat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Etat{" + "id=" + id + ", etat=" + etat + '}';
    }
    public static Etat getById(int id) throws SQLException {
        Etat tempEtat = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM etat WHERE idEtat=" + id);
        if (rs.next()) {
            tempEtat = new Etat(rs.getInt("idEtat"), rs.getString("etat"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return tempEtat;
    }
    
    public List<Etat> getEtats() {

        Connection connexion = null;
        Statement ps = null;
        ResultSet rs = null;
        List<Etat> etats = new ArrayList<Etat>();
        try {
            connexion = Database.getConnection();

            String sql = "SELECT * FROM etat";
            ps = connexion.createStatement();
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Etat tempEtat = new Etat(rs.getInt("idEtat"), rs.getString("etat"));
                etats.add(tempEtat);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            try {
                Database.close(rs);
                Database.close(ps);
                Database.close(connexion);
            } catch (SQLException ex) {
                Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return etats;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        // Commencer une transaction
        connection.setAutoCommit(false);
        try {
            // Inserer le produit
            String sql = "INSERT INTO etat(etat) VALUES(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, etat);
            
            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id
            Statement maxStmt = connection.createStatement();
            ResultSet rs = maxStmt.executeQuery("SELECT MAX(idEtat) AS idEtat FROM etat");
            rs.next();
            id = rs.getInt("idEtat");
            rs.close();
            maxStmt.close();
            // Valider
            connection.commit();
        } catch (SQLException exc) {
            connection.rollback();
            exc.printStackTrace();
            throw exc;
        } finally {
            connection.close();
        }
    }

    public void delete() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM etat WHERE idEtat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void update() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "UPDATE etat SET etat=? WHERE idEtat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
