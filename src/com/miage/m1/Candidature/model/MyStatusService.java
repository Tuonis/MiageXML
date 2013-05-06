package com.miage.m1.Candidature.model;


import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.service.StatusService;

/**
 * Gestion des erreurs : quand une ressource serveur lève une exception
 * ResourceException, le statut et le message de cette exception est
 * utilisé par ce service pour produire la réponse.
 * @author mplasse
 */
public class MyStatusService extends StatusService {
  /** Description de l'exception en cas d'erreur client.
   * Valeur standard sinon.
   * @see StatusService#getRepresentation(org.restlet.data.Status,
   * org.restlet.Request, org.restlet.Response)
   */
  @Override
  public Representation getRepresentation(Status status, Request request,
      Response response) {
    Representation result = null;
    if (status.isClientError()) {
      result = new StringRepresentation(status.getDescription());
    }
    else {
      result = super.getRepresentation(status, request, response);
    }
    return result;
  }
}
