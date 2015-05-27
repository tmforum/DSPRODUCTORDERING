package org.tmf.dsmapi.hub;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.jaxrs.Report;
import org.tmf.dsmapi.productOrder.event.Event;
import org.tmf.dsmapi.productOrder.event.EventTypeEnum;
import org.tmf.dsmapi.productOrder.model.Note;
import org.tmf.dsmapi.productOrder.model.OrderItem;
import org.tmf.dsmapi.productOrder.model.Product;
import org.tmf.dsmapi.productOrder.model.ProductCharacteristic;
import org.tmf.dsmapi.productOrder.model.ProductOffering;
import org.tmf.dsmapi.productOrder.model.ProductOrder;
import org.tmf.dsmapi.productOrder.model.Reference;
import org.tmf.dsmapi.productOrder.model.State;

@Stateless
@Path("/productOrdering/v2/hub")
public class HubResource {

    @EJB
    HubFacade hubFacade;

    public HubResource() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(Hub entity) throws BadUsageException {
        entity.setId(null);
        hubFacade.create(entity);
        //201
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @DELETE
    public Report deleteAllHub() {

        int previousRows = hubFacade.count();
        hubFacade.removeAll();
        int currentRows = hubFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) throws UnknownResourceException {
        Hub hub = hubFacade.find(id);
        if (null == hub) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            hubFacade.remove(id);
            // 204
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    @Produces({"application/json"})
    public List<Hub> findAll() {
        return hubFacade.findAll();
    }

    @GET
    @Produces({"application/json"})
    @Path("proto/productOrder/event")
    public Event protoproductorderevent() {
        Event event = new Event();
        EventTypeEnum x = EventTypeEnum.orderCreationNotification;
        event.setEventType(x);
        event.setEventTime(new Date());
        event.setId("42");
        
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
        
        event.setResource(po);
        
        return event;
    }
    
}
