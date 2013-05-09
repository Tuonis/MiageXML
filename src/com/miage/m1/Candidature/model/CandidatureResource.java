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
public class CandidatureResource extends ServerResource {
    
    /**
     * Id de la candidature correspondante
     */
    int idCandidat;
    /**
     * Id de la candidature correspondante
     */
    int idPromotion;
    /**
     * La candidature correspondante
     */
    Candidature candidature;
    /**
     * Representation retournée
     */
    Representation resultat;
    /**
     * Erreurs possibles
     */
    List<String> erreurs;

    protected void init() {
        String idAttribute1 = getRequest().getAttributes().get("idCandidat").toString();
        String idAttribute2 = getRequest().getAttributes().get("idPromotion").toString();
        try {
            idCandidat = Integer.parseInt(idAttribute1);
            idPromotion = Integer.parseInt(idAttribute2);
            if (idCandidat <= 0) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotPositiveInteger");
            }
            if (idPromotion <= 0) {
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
        candidature = candidature.getCandidature(idCandidat,idPromotion);
        if (candidature == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("candidature");
        doc.appendChild(root);
        root.setAttribute("idCandidat", String.valueOf(candidature.getIdCandidat()));
        root.setAttribute("idEtat", String.valueOf(candidature.getIdEtat()));
        root.setAttribute("idPromotion", String.valueOf(candidature.getIdPromotion()));
        root.setAttribute("motivation", candidature.getMotivation());
        root.setAttribute("dateCandidature", candidature.getDateCandidature());

        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }
    
    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        candidature = candidature.getCandidature(idCandidat,idPromotion);
        if (candidature == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        Integer idEtat = Integer.parseInt(form.getFirstValue("idEtat"));
        String motivation = form.getFirstValue("motivation");
        String dateCandidature = form.getFirstValue("dateCandidature");
        if (idEtat == null && motivation == null && dateCandidature == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (idEtat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idEtatVide");
        }else {
                candidature.setIdEtat(idEtat);
        }

        if (motivation != null) {
            if (motivation.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "motivationVide");
            } else {
                candidature.setMotivation(motivation);
            }
        }

        if (dateCandidature != null) {
            if (dateCandidature.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "dateCandidatureVide");
            } else {
                candidature.setDateCandidature(dateCandidature);
            }
        }

        try {
            candidature.update();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }
    
}
