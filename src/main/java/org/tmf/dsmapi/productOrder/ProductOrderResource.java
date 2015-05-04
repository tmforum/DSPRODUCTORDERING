package org.tmf.dsmapi.productOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.jaxrs.PATCH;
import org.tmf.dsmapi.commons.utils.Jackson;
import org.tmf.dsmapi.commons.utils.URIParser;
import org.tmf.dsmapi.productOrder.event.Event;
import org.tmf.dsmapi.productOrder.event.EventFacade;
import org.tmf.dsmapi.productOrder.event.EventPublisherLocal;
import org.tmf.dsmapi.productOrder.model.ProductOrder;

@Stateless
@Path("productOrder")
public class ProductOrderResource {

    @EJB
    ProductOrderFacade productOrderingManagementFacade;
    @EJB
    EventFacade eventFacade;
    @EJB
    EventPublisherLocal publisher;

    public ProductOrderResource() {
    }

    /**
     * Test purpose only
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(ProductOrder entity) throws BadUsageException, UnknownResourceException {
        productOrderingManagementFacade.checkCreation(entity);
        productOrderingManagementFacade.create(entity);
        entity.setHref("href/".concat(Long.toString(entity.getId())));
        productOrderingManagementFacade.edit(entity);
        publisher.createNotification(entity, new Date());
        
        ProductOrder productOrderingManagement = null;
        // 201 BUG NEED TO REFIND TO IGNORE HJID UNKNOWN CAUSE
        try {
            productOrderingManagement = productOrderingManagementFacade.find(entity.getId());
        } catch (UnknownResourceException ex) {
            Logger.getLogger(ProductOrderResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Response response = Response.status(Response.Status.CREATED).entity(productOrderingManagement).build();
        return response;
    }

    @GET
    @Produces({"application/json"})
    public Response find(@Context UriInfo info) throws BadUsageException {

        // search queryParameters
        MultivaluedMap<String, String> queryParameters = info.getQueryParameters();

        Map<String, List<String>> mutableMap = new HashMap();
        for (Map.Entry<String, List<String>> e : queryParameters.entrySet()) {
            mutableMap.put(e.getKey(), e.getValue());
        }

        // fields to filter view
        Set<String> fieldSet = URIParser.getFieldsSelection(mutableMap);

        Set<ProductOrder> resultList = findByCriteria(mutableMap);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(URIParser.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(URIParser.ID_FIELD);
            List<ObjectNode> nodeList = Jackson.createNodes(resultList, fieldSet);
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    // return Set of unique elements to avoid List with same elements in case of join
    private Set<ProductOrder> findByCriteria(Map<String, List<String>> criteria) throws BadUsageException {

        List<ProductOrder> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = productOrderingManagementFacade.findByCriteria(criteria, ProductOrder.class);
        } else {
            resultList = productOrderingManagementFacade.findAll();
        }
        if (resultList == null) {
            return new LinkedHashSet<ProductOrder>();
        } else {
            return new LinkedHashSet<ProductOrder>(resultList);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response get(@PathParam("id") long id, @Context UriInfo info) throws UnknownResourceException {

        // search queryParameters
        MultivaluedMap<String, String> queryParameters = info.getQueryParameters();

        Map<String, List<String>> mutableMap = new HashMap();
        for (Map.Entry<String, List<String>> e : queryParameters.entrySet()) {
            mutableMap.put(e.getKey(), e.getValue());
        }

        // fields to filter view
        Set<String> fieldSet = URIParser.getFieldsSelection(mutableMap);

        ProductOrder productOrderingManagement = productOrderingManagementFacade.find(id);
        Response response;
       
        // If the result list (list of bills) is not empty, it conains only 1 unique bill
        if (productOrderingManagement != null) {
            // 200
            if (fieldSet.isEmpty() || fieldSet.contains(URIParser.ALL_FIELDS)) {
                response = Response.ok(productOrderingManagement).build();
            } else {
                fieldSet.add(URIParser.ID_FIELD);
                ObjectNode node = Jackson.createNode(productOrderingManagement, fieldSet);
                response = Response.ok(node).build();
            }
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        try {
            ProductOrder entity = productOrderingManagementFacade.find(id);

            // Event deletion
            publisher.removeNotification(entity, new Date());
            try {
                //Pause for 4 seconds to finish notification
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                // Log someting to the console (should never happen)
            }
            // remove event(s) binding to the resource
            List<Event> events = eventFacade.findAll();
            for (Event event : events) {
                if (event.getResource().getId().equals(id)) {
                    eventFacade.remove(event.getId());
                }
            }
            //remove resource
            productOrderingManagementFacade.remove(id);

            // 200 
            Response response = Response.ok(entity).build();
            return response;
        } catch (UnknownResourceException ex) {
            Response response = Response.status(Response.Status.NOT_FOUND).build();
            return response;
        }
    }

    @PATCH
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response patch(@PathParam("id") long id, ProductOrder partialProduct) throws BadUsageException, UnknownResourceException {
        Response response = null;
        ProductOrder currentProduct = productOrderingManagementFacade.updateAttributs(id, partialProduct);
        
        // 201 OK + location
        response = Response.status(Response.Status.CREATED).entity(currentProduct).build();

        return response;
    }

}
