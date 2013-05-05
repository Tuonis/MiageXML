/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
}
