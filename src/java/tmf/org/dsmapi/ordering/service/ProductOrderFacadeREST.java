/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("tmf.org.dsmapi.ordering.productorder")
public class ProductOrderFacadeREST extends AbstractFacade<ProductOrder> {
    @PersistenceContext(unitName = "DSProductOrderingPU")
    private EntityManager em;

    public ProductOrderFacadeREST() {
        super(ProductOrder.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ProductOrder entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ProductOrder entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ProductOrder find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ProductOrder> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ProductOrder> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
