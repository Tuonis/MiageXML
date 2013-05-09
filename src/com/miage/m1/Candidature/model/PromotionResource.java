/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Kentish
 */
public class PromotionResource extends ServerResource {

    /**
     * Nom de la promotion correspondante
     */
    int id;
    /**
     * La promotion correspondante
     */
    Promotion promotion;
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
        promotion = promotion.getById(id);
        if (promotion == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("promotion");
        doc.appendChild(root);
        root.setAttribute("id", String.valueOf(promotion.getId()));
        root.setAttribute("nom", promotion.getNom());
        root.setAttribute("dateDeb", promotion.getDateDeb());
        root.setAttribute("dateFin", promotion.getDateDeb());
        root.setAttribute("periode", promotion.getPeriode());

        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }

    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        promotion = promotion.getById(id);
        if (promotion == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String dateDeb = form.getFirstValue("dateDeb");
        String dateFin = form.getFirstValue("dateFin");
        String periode = form.getFirstValue("periode");
        if (nom == null && dateDeb == null && dateFin == null && periode == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            if (nom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "nomVide");
            } else {
                promotion.setNom(nom);
            }
        }

        if (dateDeb != null) {
            if (dateDeb.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "dateDebVide");
            } else {
                promotion.setDateDeb(dateDeb);
            }
        }

        if (dateFin != null) {
            if (dateFin.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "dateFinVide");
            } else {
                promotion.setDateFin(dateFin);
            }
        }

        if (periode != null) {
            if (periode.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "periodeVide");
            } else {
                promotion.setPeriode(periode);
            }
        }

        try {
            promotion.update();
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
            promotion.delete();
            setStatus(Status.SUCCESS_NO_CONTENT);
        }
        return resultat;
    }
}
