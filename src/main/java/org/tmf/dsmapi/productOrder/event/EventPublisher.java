package org.tmf.dsmapi.productOrder.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.hub.Hub;
import org.tmf.dsmapi.hub.HubFacade;
import org.tmf.dsmapi.productOrder.model.ProductOrder;

/**
 *
 * @author pierregauthier should be async or called with MDB
 */
@Stateless
@Asynchronous
public class EventPublisher implements EventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    @EJB
    EventFacade eventFacade;
    @EJB
    RESTEventPublisherLocal restEventPublisherLocal;

    /** 
     * Add business logic below. (Right-click in editor and choose
     * "Insert Code > Add Business Method")
     * Access Hubs using callbacks and send to http publisher 
     *(pool should be configured around the RESTEventPublisher bean)
     * Loop into array of Hubs
     * Call RestEventPublisher - Need to implement resend policy plus eviction
     * Filtering is done in RestEventPublisher based on query expression
    */ 
    @Override
    public void publish(Event event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(EventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    
    public void createNotification(ProductOrder bean, String reason, Date date) {
      
        Event event = new Event();
        event.setResource(bean);
        event.setDate(date);
        event.setReason(reason);
        event.setEventType(EventTypeEnum.ProductOrderCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(ProductOrder bean, String reason, Date date) {
        Event event = new Event();
        event.setResource(bean);
        event.setDate(date);
        event.setReason(reason);
        event.setEventType(EventTypeEnum.ProductOrderDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(ProductOrder bean, String reason, Date date) {
        Event event = new Event();
        event.setResource(bean);
        event.setDate(date);
        event.setReason(reason);
        event.setEventType(EventTypeEnum.ProductOrderUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(ProductOrder bean, String reason, Date date) {
        Event event = new Event();
        event.setResource(bean);
        event.setDate(date);
        event.setReason(reason);
        event.setEventType(EventTypeEnum.ProductOrderValueChangeNotification);
        publish(event);
    }
}
