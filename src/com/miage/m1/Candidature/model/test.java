/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kentish
 */
public class test {
    public static void main(String[] args){
        Candidat candidat=new Candidat();
       Candidat c1=candidat.getCandidats().get(0);
       System.out.println(c1.getNom());
       candidat.setNom("Munusami");
       candidat.setPrenom("Kentish");
       candidat.setMail("kentish.munusami@gmail.com");
       candidat.setSituation("master miage");
       candidat.setTelephone("0612345678");
       candidat.setAdresse("34 rue des sculpteurs 93240 Stains");
       candidat.setMdp("ken");
        try {
            candidat.insert();
        } catch (SQLException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
