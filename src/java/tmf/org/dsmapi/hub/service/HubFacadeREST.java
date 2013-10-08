/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub.service;

import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;

import tmf.org.dsmapi.hub.Hub;
import tmf.org.dsmapi.hub.HubEvent;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.ordering.ProductOrder;
import tmf.org.dsmapi.ordering.service.FacadeRestUtil;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("hub")
public class HubFacadeREST {

    @EJB
    HubFacade manager;

    public HubFacadeREST() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(Hub entity) {
        entity.setId(null);
        manager.create(entity);
        Response response = Response.ok(entity).build();
        return response;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        manager.remove(manager.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") String id, @Context UriInfo info) {
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(info.getQueryParameters());

        Hub entity = manager.find(id);
        Response response;
        if (entity != null) {
            // 200
            if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
                response = Response.ok(entity).build();
            } else {
                fieldSet.add(FacadeRestUtil.ID_FIELD);
                ObjectNode node = FacadeRestUtil.createNodeViewWithFields(entity, fieldSet);
                response = Response.ok(node).build();
            }
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @GET
    @Produces({"application/json"})
    public List<Hub> findAll() {
        return manager.findAll();
    }

    @POST
    @Path("listener")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public void publishEvent(HubEvent event) {

        System.out.println("HubEvent =" + event);
        System.out.println("Event = " + event.getEvent().toString());
        System.out.println("Event type = " + event.getEventType().getText());

    }

    @GET
    @Path("proto")
    @Produces({"application/json"})
    public Hub hubProto() {
        Hub hub = new Hub();
        hub.setCallback("callback");
        hub.setQuery("queryString");
        hub.setId("id");
        return hub;
    }

    @GET
    @Path("eventProto")
    @Produces({"application/json"})
    public HubEvent eventProto() {
        HubEvent event = new HubEvent();
        event.setEvent(proto());
        event.setEventType(ProductOrderEventTypeEnum.OrderCreateNotification);
        System.out.println("Event = " + event.getEvent().toString());
        System.out.println("Event type = " + event.getEventType().getText());
        return event;
    }

    public ProductOrder proto() {
        ProductOrder po = new ProductOrder();
        return po;

    }
}
