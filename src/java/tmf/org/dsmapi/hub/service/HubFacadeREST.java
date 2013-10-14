/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub.service;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;

import tmf.org.dsmapi.hub.Hub;
import tmf.org.dsmapi.hub.HubEvent;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.ordering.OrderItemStateEnum;
import tmf.org.dsmapi.ordering.OrderState;
import tmf.org.dsmapi.ordering.Product;
import tmf.org.dsmapi.ordering.ProductCharacteristic;
import tmf.org.dsmapi.ordering.ProductOrder;
import tmf.org.dsmapi.ordering.ProductOrderItem;
import tmf.org.dsmapi.ordering.RefInfo;
import tmf.org.dsmapi.ordering.RelatedParty;
import tmf.org.dsmapi.ordering.Report;
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
    @EJB
    HubEventFacade hubEventManager;

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
        System.out.println("Event = " + event.getEvent());
        System.out.println("Event type = " + event.getEventType());

    }

    @DELETE
    @Path("event")
    public Report deleteAllEvents() {

        int previousRows = hubEventManager.count();
        hubEventManager.removeAll();
        int currentRows = hubEventManager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }


    @GET
    @Path("listener")
    @Produces({"application/json"})
    public Response findEvents(@Context UriInfo info) {

        // search criteria
        MultivaluedMap<String, String> criteria = FacadeRestUtil.parseFields(info);
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(criteria);

        Set<HubEvent> resultList = findByCriteria(criteria);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(FacadeRestUtil.ID_FIELD);
            List<ObjectNode> nodeList = FacadeRestUtil.createNodeListViewWithFields(resultList, fieldSet);
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    // return Set of unique elements to avoid List with same elements in case of join
    private Set<HubEvent> findByCriteria(MultivaluedMap<String, String> criteria) {
        List<HubEvent> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = hubEventManager.findByCriteria(criteria, HubEvent.class);
        } else {
            resultList = hubEventManager.findAll();
        }
        if (resultList == null) {
            return new LinkedHashSet<HubEvent>();
        } else {
            return new LinkedHashSet<HubEvent>(resultList);
        }
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
        System.out.println("Event = " + event.getEvent());
        System.out.println("Event type = " + event.getEventType());
        return event;
    }

    public ProductOrder proto() {
        ProductOrder po = new ProductOrder();

        po.setId("1");
        po.setCompletionDate(new Date());
        po.setDescription("description");
        po.setExternalID("externalId");
        po.setOrderDate(new Date());

        RelatedParty party = new RelatedParty();
        party.setReference("ref");
        party.setRole("role");
        po.setRelatedParties(Arrays.asList(party));

        ProductOrderItem item = new ProductOrderItem();
        item.setAction("action");
        item.setId("item1");

        Product product = new Product();

        ProductCharacteristic productCharacteristic = new ProductCharacteristic();
        productCharacteristic.setName("name");
        productCharacteristic.setValue("value");
        product.setProductCharacteristics(Arrays.asList(productCharacteristic));

        RefInfo refInfo = new RefInfo();
        refInfo.setDescription("description");
        refInfo.setId("id");
        refInfo.setName("name");
        product.setProductSpecification(refInfo);

        item.setProduct(product);
        RefInfo productOffering = refInfo;
        item.setProductOffering(productOffering);
        item.setState(OrderItemStateEnum.Running);

        ProductOrderItem item2 = new ProductOrderItem();
        item2.setAction("action");
        item2.setId("item2");
        item2.setProduct(product);
        item2.setProductOffering(productOffering);
        item2.setState(OrderItemStateEnum.NotStarted);

        po.setOrderItems(Arrays.asList(item, item2));

        po.setRequestedCompletionDate(new Date());
        po.setStatus(OrderState.OPEN_RUNNING);
        po.setType("type");

        return po;
    }
}
