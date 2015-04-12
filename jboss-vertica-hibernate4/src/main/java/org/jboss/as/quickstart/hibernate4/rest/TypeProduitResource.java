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

import org.jboss.as.quickstart.hibernate4.domain.TypeProduit;
import org.jboss.as.quickstart.hibernate4.service.TypeProduitService;

@Path("/typeProduit")
public class TypeProduitResource {

    @Inject
    private TypeProduitService typeProduitService;

    @GET
    @Produces({ "application/json" })
    public List<TypeProduit> getAll() {
	return typeProduitService.findAll();

    }

    @GET
    @Path("{id}")
    @Produces({ "application/json" })
    public Object getById(@PathParam("id") Long id) {
	return typeProduitService.findById(id);
    }

    @DELETE
    @Path("{id}")
    @Produces({ "application/json" })
    public boolean delete(@PathParam("id") Long id) throws Exception {

	return typeProduitService.delete(id);
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public TypeProduit create(TypeProduit typeProduit) throws Exception {

	return typeProduitService.create(typeProduit);
    }

    @PUT
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public boolean save(TypeProduit typeProduit) throws Exception {

	return typeProduitService.save(typeProduit);
    }

}
