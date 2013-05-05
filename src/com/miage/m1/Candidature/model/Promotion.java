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
 * @author Kentish
 */
public class Promotion {

    private int id;
    private String nom;
    private String dateDeb;
    private String dateFin;
    private String periode;

    public Promotion(int id, String nom, String dateDeb, String dateFin, String periode) {
        this.id = id;
        this.nom = nom;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.periode = periode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Promotion other = (Promotion) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
            return false;
        }
        if ((this.dateDeb == null) ? (other.dateDeb != null) : !this.dateDeb.equals(other.dateDeb)) {
            return false;
        }
        if ((this.dateFin == null) ? (other.dateFin != null) : !this.dateFin.equals(other.dateFin)) {
            return false;
        }
        if ((this.periode == null) ? (other.periode != null) : !this.periode.equals(other.periode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", nom=" + nom + ", dateDeb=" + dateDeb + ", dateFin=" + dateFin + ", periode=" + periode + '}';
    }

    public static Promotion getById(int id) throws SQLException {
        Promotion promotion = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM promotion WHERE idPromotion=" + id);
        if (rs.next()) {
            promotion = new Promotion(rs.getInt("idPromotion"), rs.getString("nom"), rs.getString("dateDeb"), rs.getString("dateFin"), rs.getString("periode"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return promotion;
    }

    public static Promotion getByNom(String nom) throws SQLException {
        Promotion promotion = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM promotion WHERE nom=" + nom);
        if (rs.next()) {
            promotion = new Promotion(rs.getInt("idPromotion"), rs.getString("nom"), rs.getString("dateDeb"), rs.getString("dateFin"), rs.getString("periode"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return promotion;
    }

    public List<Promotion> getPromotions() {

        Connection connexion = null;
        Statement ps = null;
        ResultSet rs = null;
        List<Promotion> promotions = new ArrayList<Promotion>();
        try {
            connexion = Database.getConnection();

            String sql = "SELECT * FROM promotion";
            ps = connexion.createStatement();
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Promotion promotion = new Promotion(rs.getInt("idPromotion"), rs.getString("nom"), rs.getString("dateDeb"), rs.getString("dateFin"), rs.getString("periode"));
                promotions.add(promotion);
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
        return promotions;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        // Commencer une transaction
        connection.setAutoCommit(false);
        try {
            // Inserer le produit
            String sql = "INSERT INTO promotion(nom, dateDeb, dateFin, periode) VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, dateDeb);
            stmt.setString(3, dateFin);
            stmt.setString(4, periode);
            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id
            Statement maxStmt = connection.createStatement();
            ResultSet rs = maxStmt.executeQuery("SELECT MAX(no_produit) AS id FROM promotion");
            rs.next();
            id = rs.getInt("id");
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
        String sql = "DELETE FROM promotion WHERE idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    public void update() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "UPDATE promotion SET nom=?, dateDeb=?, dateFin=?, periode=? WHERE idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nom);
        stmt.setString(2, dateDeb);
        stmt.setString(3, dateFin);
        stmt.setString(4, periode);
        stmt.setInt(5, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
