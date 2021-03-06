/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Tuonis Home
 */
public class CandidatResource extends ServerResource {

    /**
     * Id du candidat correspondant
     */
    int id;
    /**
     * Le candidat correspondant
     */
    Candidat candidat;
    /**
     * Representation retournée
     */
    Representation resultat;
    /**
     * Erreurs possibles
     */
    List<String> erreurs;

    protected void init() {
        String idAttribute = getRequest().getAttributes().get("id").toString();
        try {
            id = Integer.parseInt(idAttribute);
            if (id <= 0) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotPositiveInteger");
            }
        } catch (NumberFormatException exc) {
            // Indiquer que la requete est mal formee
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotInteger");
        }
    }

    @Get("xml")
    public Representation doGet() throws SQLException, IOException {
        init();
        candidat = candidat.getById(id);
        if (candidat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("candidat");
        doc.appendChild(root);
        root.setAttribute("id", String.valueOf(candidat.getId()));
        root.setAttribute("nom", candidat.getNom());
        root.setAttribute("prenom", candidat.getPrenom());
        root.setAttribute("telephone", candidat.getTelephone());
        root.setAttribute("mail", candidat.getMail());
        root.setAttribute("adresse", candidat.getAdresse());
        root.setAttribute("situation", candidat.getSituation());
        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }

    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        candidat = candidat.getById(id);
        if (candidat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String prenom = form.getFirstValue("prenom");
        String tel = form.getFirstValue("telephone");
        String mail = form.getFirstValue("mail");
        String adresse = form.getFirstValue("adresse");
        String situation = form.getFirstValue("situation");
        if (nom == null && prenom == null && tel == null && mail == null && adresse == null && situation == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            if (nom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "nomVide");
            } else {
                candidat.setNom(nom);
            }
        }

        if (prenom != null) {
            if (prenom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "prenomVide");
            } else {
                candidat.setPrenom(prenom);
            }
        }

        if (tel != null) {
            if (tel.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "telephoneVide");
            } else {
                candidat.setTelephone(tel);
            }
        }

        if (mail != null) {
            if (mail.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "mailVide");
            } else {
                candidat.setMail(mail);
            }
        }

        if (adresse != null) {
            if (adresse.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "adresseVide");
            } else {
                candidat.setAdresse(adresse);
            }
        }

        if (situation != null) {
            if (situation.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "situationVide");
            } else {
                candidat.setSituation(situation);
            }
        }

        try {
            candidat.update();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }

    @Delete
    public Representation doDelete() throws SQLException {
        init();
        if (getStatus() == Status.SUCCESS_OK) {
            // Traiter cas de produit inexistant (404) : a faire
            candidat.delete();
            setStatus(Status.SUCCESS_NO_CONTENT);
        }
        return resultat;
    }
}
