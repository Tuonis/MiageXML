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
public class Candidat {

    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private String situation;
    private String adresse;
    private String mdp;

    public Candidat(int id, String nom, String prenom, String telephone, String mail, String situation, String adresse, String mdp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.mail = mail;
        this.situation = situation;
        this.adresse = adresse;
        this.mdp=mdp;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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
        final Candidat other = (Candidat) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
            return false;
        }
        if ((this.prenom == null) ? (other.prenom != null) : !this.prenom.equals(other.prenom)) {
            return false;
        }
        if ((this.telephone == null) ? (other.telephone != null) : !this.telephone.equals(other.telephone)) {
            return false;
        }
        if ((this.mail == null) ? (other.mail != null) : !this.mail.equals(other.mail)) {
            return false;
        }
        if ((this.situation == null) ? (other.situation != null) : !this.situation.equals(other.situation)) {
            return false;
        }
        if ((this.adresse == null) ? (other.adresse != null) : !this.adresse.equals(other.adresse)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Candidat{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", mail=" + mail + ", situation=" + situation + ", adresse=" + adresse + '}';
    }

    public static Candidat getById(int id) throws SQLException {
        Candidat candidat = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM candidat WHERE idCandidat=" + id);
        if (rs.next()) {
            candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("situation"), rs.getString("adresse"),rs.getString("mdp"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return candidat;
    }

    public static Candidat getByNom(String nom) throws SQLException {
        Candidat candidat = null;
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM candidat WHERE nom=" + nom);
        if (rs.next()) {
            candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("situation"), rs.getString("adresse"),rs.getString("mdp"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return candidat;
    }

    public List<Candidat> getCandidats() {

        Connection connexion = null;
        Statement ps = null;
        ResultSet rs = null;
        List<Candidat> candidats = new ArrayList<Candidat>();
        try {
            connexion = Database.getConnection();

            String sql = "SELECT * FROM candidat";
            ps = connexion.createStatement();
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Candidat candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("situation"), rs.getString("adresse"),rs.getString("mdp"));
                candidats.add(candidat);
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
        return candidats;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        // Commencer une transaction
        connection.setAutoCommit(false);
        try {
            // Inserer le produit
            String sql = "INSERT INTO candidat(nom, prenom, telephone, mail, situation, adresse, mdp) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, telephone);
            stmt.setString(4, mail);
            stmt.setString(5, situation);
            stmt.setString(6, adresse);
            stmt.setString(7, mdp);
            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id
            Statement maxStmt = connection.createStatement();
            ResultSet rs = maxStmt.executeQuery("SELECT MAX(idCandidat) AS id FROM candidat");
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
        String sql = "DELETE FROM candidat WHERE idCandidat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void update() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "UPDATE candidat SET nom=?, prenom=?, telephone=?, mail=?, situation=?, adresse=?, mdp=? WHERE idCandidat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nom);
        stmt.setString(2, prenom);
        stmt.setString(3, telephone);
        stmt.setString(4, mail);
        stmt.setString(5, situation);
        stmt.setString(6, adresse);
        stmt.setString(7, mdp);
        stmt.setInt(8, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
