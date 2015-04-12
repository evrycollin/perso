package org.jboss.as.quickstart.hibernate4.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.as.quickstart.hibernate4.domain.Commande;
import org.jboss.as.quickstart.hibernate4.service.CommandeService;

@Path("/commande")
public class CommandeResource {

    @Inject
    private CommandeService commandeService;

    @GET
    @Produces({ "application/json" })
    public List<Commande> getAll() {
	return commandeService.findAll();

    }

    @GET
    @Path("{id}")
    @Produces({ "application/json" })
    public Commande getById(@PathParam("id") Long id) {
	return commandeService.findById(id);
    }

    @DELETE
    @Path("{id}")
    @Produces({ "application/json" })
    public boolean delete(@PathParam("id") Long id) throws Exception {

	return commandeService.delete(id);
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Commande create(Commande commande) throws Exception {

	return commandeService.create(commande);
    }

    @PUT
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public boolean save(Commande commande) throws Exception {

	return commandeService.save(commande);
    }

}
