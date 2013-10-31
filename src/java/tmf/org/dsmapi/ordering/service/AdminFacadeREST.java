/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import java.util.LinkedList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import tmf.org.dsmapi.hub.service.HubEventFacade;
import tmf.org.dsmapi.hub.service.HubFacade;
import tmf.org.dsmapi.ordering.ProductOrder;
import tmf.org.dsmapi.ordering.Report;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("admin")
public class AdminFacadeREST {

    @EJB
    ProductOrderFacade manager;
    @EJB
    HubEventFacade eventManager;
    @EJB
    HubFacade hubManager;    

    public AdminFacadeREST() {
    }

    @DELETE
    @Path("productOrder/{id}")
    public Report remove(@PathParam("id") String id) {

        manager.remove(manager.find(id));
        eventManager.removeAll();
        int previousRows = manager.count();
        manager.removeAll();
        int currentRows = manager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @GET
    @Path("productOrder/count")
    @Produces({"application/json"})
    public Report count() {
        return new Report(manager.count());
    }

    @POST
    @Path("productOrder")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createList(LinkedList<ProductOrder> entities) {
        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = manager.count();
        int affectedRows;

        affectedRows = manager.create(entities);

        Report stat = new Report(manager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @DELETE
    @Path("productOrder")
    public Report deleteAll() {

        eventManager.removeAll();
        int previousRows = manager.count();
        manager.removeAll();
        int currentRows = manager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }
    
    /**
     *
     * @return
     */
    @DELETE
    @Path("hub")
    public Report deleteAllHub() {

        int previousRows = hubManager.count();
        hubManager.removeAll();
        int currentRows = hubManager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }
    
    @DELETE
    @Path("event")
    public Report deleteAllEvent() {

        int previousRows = eventManager.count();
        eventManager.removeAll();
        int currentRows = eventManager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }    
}
