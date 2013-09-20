/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import java.util.List;
import javax.ejb.EJB;
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
import tmf.org.dsmapi.hub.service.PublisherLocal;
import tmf.org.dsmapi.ordering.ProductOrder;
import tmf.org.dsmapi.ordering.ProductOrderItem;
import tmf.org.dsmapi.ordering.RelatedParty;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("productOrder")
public class ProductOrderFacadeREST extends AbstractFacade<ProductOrder> {
    
    @EJB
    WorkFlowLocalLocal workflow;
    @EJB
    PublisherLocal publisher;
    
    
    @PersistenceContext(unitName = "DSProductOrderingPU")
    private EntityManager em;

    public ProductOrderFacadeREST() {
        super(ProductOrder.class);
    }

    @POST
    @Consumes({ "application/json"})
    @Produces({ "application/json"})
    public ProductOrder createOrder(ProductOrder entity) {
        super.create(entity);
        //creating order calling workflow
        workflow.execute(entity);
        return entity;
    }

    @PUT
    @Override
    @Consumes({ "application/json"})
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
    @Produces({ "application/json"})
    public ProductOrder find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("proto")
    @Produces({ "application/json"})
    public ProductOrder proto() {
        ProductOrder po = new ProductOrder();
       
        String par = null;
        po.setCompletionDate(par);
        po.setDescription(par);
        po.setExternalID(par);
        po.setOrderDate(par);
        RelatedParty[] rel = null;
        po.setRelatedParties(rel);
        ProductOrderItem[] oitems = null;
        po.setOrderItems(oitems);
        String rdate = null;
        po.setRequestedCompletionDate(rdate);
        String status = null;
        po.setStatus(status);
        String type = null;
        po.setType(type);
        
        return po;
    }

    @GET
    @Override
    @Produces({ "application/json"})
    public List<ProductOrder> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
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
