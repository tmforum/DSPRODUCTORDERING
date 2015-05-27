package org.tmf.dsmapi.productOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.jaxrs.Report;
import org.tmf.dsmapi.productOrder.event.Event;
import org.tmf.dsmapi.productOrder.event.EventFacade;
import org.tmf.dsmapi.productOrder.event.EventPublisherLocal;
import org.tmf.dsmapi.productOrder.model.Note;
import org.tmf.dsmapi.productOrder.model.OrderItem;
import org.tmf.dsmapi.productOrder.model.Product;
import org.tmf.dsmapi.productOrder.model.ProductCharacteristic;
import org.tmf.dsmapi.productOrder.model.ProductOffering;
import org.tmf.dsmapi.productOrder.model.ProductOrder;
import org.tmf.dsmapi.productOrder.model.Reference;
import org.tmf.dsmapi.productOrder.model.RelatedParty;
import org.tmf.dsmapi.productOrder.model.State;

@Stateless
@Path("/admin/productOrder")
public class AdminResource {

    @EJB
    ProductOrderFacade productOrderingManagementFacade;
    @EJB
    EventFacade eventFacade;
//    @EJB
//    EventPublisherLocal publisher;

    @GET
    @Produces({"application/json"})
    public List<ProductOrder> findAll() {
        return productOrderingManagementFacade.findAll();
    }

    /**
     *
     * For test purpose only
     *
     * @param entities
     * @return
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response post(List<ProductOrder> entities, @Context UriInfo info) throws UnknownResourceException {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = productOrderingManagementFacade.count();
        int affectedRows = 0;

        // Try to persist entities
        try {
            for (ProductOrder entitie : entities) {
                productOrderingManagementFacade.checkCreation(entitie);
                productOrderingManagementFacade.create(entitie);
                entitie.setHref(info.getAbsolutePath() + "/" + Long.toString(entitie.getId()));
                productOrderingManagementFacade.edit(entitie);
                affectedRows = affectedRows + 1;
//                publisher.createNotification(entitie, new Date());
            }
        } catch (BadUsageException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        Report stat = new Report(productOrderingManagementFacade.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.status(Response.Status.CREATED).entity(stat).build();
//        return Response.created(null).
//                entity(stat).
//                build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response update(@PathParam("id") long id, ProductOrder entity) throws UnknownResourceException {
        Response response = null;
        ProductOrder productOrderingManagement = productOrderingManagementFacade.find(id);
        if (productOrderingManagement != null) {
            entity.setId(id);
            productOrderingManagementFacade.edit(entity);
//            publisher.valueChangeNotification(entity, new Date());
            // 200 OK + location
            response = Response.status(Response.Status.OK).entity(entity).build();

        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    /**
     *
     * For test purpose only
     *
     * @return
     * @throws org.tmf.dsmapi.commons.exceptions.UnknownResourceException
     */
    @DELETE
    public Report deleteAll() throws UnknownResourceException {

        eventFacade.removeAll();
        int previousRows = productOrderingManagementFacade.count();
        productOrderingManagementFacade.removeAll();
        List<ProductOrder> pis = productOrderingManagementFacade.findAll();
        for (ProductOrder pi : pis) {
            delete(pi.getId());
        }

        int currentRows = productOrderingManagementFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    /**
     *
     * For test purpose only
     *
     * @param id
     * @return
     * @throws UnknownResourceException
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws UnknownResourceException {
        int previousRows = productOrderingManagementFacade.count();
        ProductOrder entity = productOrderingManagementFacade.find(id);

        // Event deletion
//            publisher.removeNotification(entity, new Date());
        try {
            //Pause for 4 seconds to finish notification
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AdminResource.class.getName()).log(Level.SEVERE, null, ex);
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

        int affectedRows = 1;
        Report stat = new Report(productOrderingManagementFacade.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 200 
        Response response = Response.ok(stat).build();
        return response;
    }

    @GET
    @Produces({"application/json"})
    @Path("event")
    public List<Event> findAllEvents() {
        return eventFacade.findAll();
    }

    @DELETE
    @Path("event")
    public Report deleteAllEvent() {

        int previousRows = eventFacade.count();
        eventFacade.removeAll();
        int currentRows = eventFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("event/{id}")
    public Response deleteEvent(@PathParam("id") String id) throws UnknownResourceException {

        int previousRows = eventFacade.count();
        List<Event> events = eventFacade.findAll();
        for (Event event : events) {
            if (event.getResource().getId().equals(id)) {
                eventFacade.remove(event.getId());

            }
        }
        int currentRows = eventFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 200 
        Response response = Response.ok(stat).build();
        return response;
    }

    /**
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces({"application/json"})
    public Report count() {
        return new Report(productOrderingManagementFacade.count());
    }

    @GET
    @Produces({"application/json"})
    @Path("proto")
    public ProductOrder proto() {
        ProductOrder po = new ProductOrder();
        po.setId(new Long(123));
        po.setHref("http://serverLocalisation:port/DSProductOrdering/api/productOrdering/v2/productOrder/123");
        po.setExternalId("");
        po.setPriority("4");
        po.setDescription("product Irder Description");
        po.setCategory("uncategorized");
        po.setState(State.Acknowledged);
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2015, 05, 15);
        po.setOrderDate(gc.getTime());
        po.setCompletionDate(null);
        po.setRequestedStartDate(new Date());
        po.setRequestedCompletionDate(null);
        po.setExpectedCompletionDate(null);
        po.setNotificationContact("");
        
        List<Note> l_note = new ArrayList<Note>();
        Note note = new Note();
        note.setText("A free text detailing the note");
        l_note.add(note);
        po.setNote(l_note);
        
        List<Reference> l_ref = new ArrayList<Reference>();
        Reference ref = new Reference();
        ref.setRole("customer");
        ref.setId("2451");
        ref.setHref("http://serverlocation:port/partyManagement/customer/2451");
        ref.setName("John Doe");
        l_ref.add(ref);
        ref = new Reference();
        ref.setRole("seller");
        ref.setId("598");
        ref.setHref("http://serverlocation:port/partnerManagement/partner/598");
        l_ref.add(ref);
        po.setRelatedParty(l_ref);
        
        List<OrderItem> l_orderItem = new ArrayList<OrderItem>();
        OrderItem orderItem = new OrderItem();
        orderItem.setId("1");
        orderItem.setAction("add");
        
        l_ref = new ArrayList<Reference>();
        ref = new Reference();
        ref.setId("1789");
        ref.setHref("http://serverlocation:port/billingManagement/billingAccount/1789");
        l_ref.add(ref);
        orderItem.setBillingAccount(l_ref);
        
        orderItem.setAppointment("Orderitem 1 appointment");
        
        ProductOffering productOffering = new ProductOffering();
        productOffering.setId("42");
        productOffering.setHref("http: //serverlocation:port/catalogManagement/productOffering/42");
        orderItem.setProductOffering(productOffering);
        
        Product product = new Product();
        List<ProductCharacteristic> l_productCharacteristics = new ArrayList<ProductCharacteristic>();
        ProductCharacteristic pc = new ProductCharacteristic();
        pc.setName("Colour");
        pc.setValue("White");
        l_productCharacteristics.add(pc);
        pc = new ProductCharacteristic();
        pc.setName("Memory");
        pc.setValue("16");
        l_productCharacteristics.add(pc);
        product.setProductCharacteristic(l_productCharacteristics);
        orderItem.setProduct(product);
        l_orderItem.add(orderItem);
        
        orderItem = new OrderItem();
        orderItem.setId("2");
        orderItem.setAction("modify");
        product = new Product();
        l_ref = new ArrayList<Reference>();
        ref = new Reference();
        ref.setRole("user");
        ref.setId("5667443");
        ref.setHref("http://serverlocation:port/partyManagement/user/5667443");
        ref.setName("Jimmy Doe");
        l_ref.add(ref);
        product.setRelatedParty(l_ref);
        product.setId("456");
        product.setHref("http://serverlocation:port/inventoryManagement/product/456");
        orderItem.setProduct(product);
        l_orderItem.add(orderItem);
        
        orderItem = new OrderItem();
        orderItem.setId("3");
        orderItem.setAction("delete");
        product = new Product();
        product.setId("460");
        product.setHref("http://serverlocation:port/inventoryManagement/product/460");
        orderItem.setProduct(product);
        l_orderItem.add(orderItem);
        
        po.setOrderItem(l_orderItem);
        
        return po;
    }
}
