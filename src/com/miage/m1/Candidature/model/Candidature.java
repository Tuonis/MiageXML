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
public class Candidature {

    private int idCandidat;
    private int idEtat;
    private int idPromotion;
    private String motivation;
    private String dateCandidature;

    public Candidature(int idCandidat, int idEtat, int idPromotion, String motivation, String dateCandidature) {
        this.idCandidat = idCandidat;
        this.idEtat = idEtat;
        this.idPromotion = idPromotion;
        this.motivation = motivation;
        this.dateCandidature = dateCandidature;
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public int getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }

    public int getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(String dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idCandidat;
        hash = 89 * hash + this.idEtat;
        hash = 89 * hash + this.idPromotion;
        return hash;
    }

    @Override
    public String toString() {
        return "Candidature{" + "idCandidat=" + idCandidat + ", idEtat=" + idEtat + ", idPromotion=" + idPromotion + ", motivation=" + motivation + ", dateCandidature=" + dateCandidature + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Candidature other = (Candidature) obj;
        if (this.idCandidat != other.idCandidat) {
            return false;
        }
        if (this.idEtat != other.idEtat) {
            return false;
        }
        if (this.idPromotion != other.idPromotion) {
            return false;
        }
        if ((this.motivation == null) ? (other.motivation != null) : !this.motivation.equals(other.motivation)) {
            return false;
        }
        if ((this.dateCandidature == null) ? (other.dateCandidature != null) : !this.dateCandidature.equals(other.dateCandidature)) {
            return false;
        }
        return true;
    }

    public static Candidature getById(int idCandidat, int idPromotion) throws SQLException {

        Candidature candidature = null;
        Connection connection = Database.getConnection();
        PreparedStatement select = null;
        String sql = "SELECT * FROM candidature WHERE Candidat_idCandidat=? and Promotion_idPromotion=?";
        select = connection.prepareStatement(sql);
        select.setInt(1, idCandidat);
        select.setInt(2, idPromotion);
        ResultSet rs = select.executeQuery();
        if (rs.next()) {
            candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getString("dateCandidature"));
        }
        rs.close();
        select.close();
        connection.close();


        return candidature;

    }

    public static Candidature getByIdCandidat(int id) throws SQLException {

        Candidature candidature = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM candidature WHERE Candidat_idCandidat=" + id);
        if (rs.next()) {
            candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getString("dateCandidature"));
        }
        rs.close();
        stmt.close();
        connection.close();


        return candidature;

    }

    public static Candidature getByIdEtat(int id) throws SQLException {

        Candidature candidature = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM candidature WHERE Etat_idEtat=" + id);
        if (rs.next()) {
            candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getString("dateCandidature"));
        }
        rs.close();
        stmt.close();
        connection.close();


        return candidature;

    }

    public static Candidature getByIdPromotion(int id) throws SQLException {

        Candidature candidature = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM candidature WHERE Promotion_idPromotion=" + id);
        if (rs.next()) {
            candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getString("dateCandidature"));
        }
        rs.close();
        stmt.close();
        connection.close();


        return candidature;

    }

    public List<Candidature> getCandidatures() {

        Connection connexion = null;
        Statement ps = null;
        ResultSet rs = null;
        List<Candidature> candidatures = new ArrayList<Candidature>();
        try {
            connexion = Database.getConnection();

            String sql = "SELECT * FROM candidature";
            ps = connexion.createStatement();
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Candidature candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getString("dateCandidature"));
                candidatures.add(candidature);
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
        return candidatures;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        // Commencer une transaction
        connection.setAutoCommit(false);
        try {
            // Inserer le produit
            String sql = "INSERT INTO candidature(Candidat_idCandidat, Etat_idEtat, Promotion_idPromotion, motivation,dateCandidature) VALUES(?, ?, ?, ?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCandidat);
            stmt.setInt(2, idEtat);
            stmt.setInt(3, idPromotion);
            stmt.setString(4, motivation);
            stmt.setString(5, dateCandidature);

            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id

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
        String sql = "DELETE FROM candidature WHERE Candidat_idCandidat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCandidat);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
