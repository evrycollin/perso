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

import org.jboss.as.quickstart.hibernate4.domain.Produit;
import org.jboss.as.quickstart.hibernate4.service.ProduitService;

@Path("/produit")
public class ProduitResource {

    @Inject
    private ProduitService produitService;

    @GET
    @Produces({ "application/json" })
    public List<Produit> getAll() {
	return produitService.findAll();

    }

    @GET
    @Path("{id}")
    @Produces({ "application/json" })
    public Produit getById(@PathParam("id") Long id) {
	return produitService.findById(id);
    }

    @DELETE
    @Path("{id}")
    @Produces({ "application/json" })
    public boolean delete(@PathParam("id") Long id) throws Exception {

	return produitService.delete(id);
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Produit create(Produit produit) throws Exception {

	return produitService.create(produit);
    }

    @PUT
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public boolean save(Produit produit) throws Exception {

	return produitService.save(produit);
    }

}
