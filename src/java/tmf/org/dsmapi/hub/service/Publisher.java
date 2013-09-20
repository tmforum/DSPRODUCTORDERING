/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tmf.org.dsmapi.hub.Hub;
import tmf.org.dsmapi.hub.HubEvent;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.ordering.ProductOrder;


/**
 *
 * @author pierregauthier should be async or called with MDB
 */
@Stateless
//@Asynchronous bug in 7.3
@Asynchronous
public class Publisher implements PublisherLocal {

    @EJB
    HubFacade hubFacade;
    @EJB
    RESTEventPublisherLocal restEventPublisher;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //Access Hubs using callbacks and send to http publisher 
    //(pool should be configured around the RESTEventPublisher bean)
    //Loop into array of Hubs
    //Call RestEventPublisher - Need to implement resend policy plus eviction
    //Filtering is done in RestEventPublisher based on query expression
    @Override
    public void publish(Object event) {
        System.out.println("Sending Event");

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisher.publish(hub, event);


            //Thread.currentThread().sleep(1000);
        }
        System.out.println("Sending Event After");
    }

    @Override
    public void publishOrderCreateNotification(ProductOrder po, String reason, Date date) {
        HubEvent event = new HubEvent();
        event.setEvent(po);
         event.setDate(date);
        event.setReason(reason);
        event.setEventType(ProductOrderEventTypeEnum.OrderCreateNotification);
        publish(event);

    }

    @Override
    public void publishOrderStatusChangedNotification(ProductOrder po, String reason, Date date) {

        HubEvent event = new HubEvent();
        event.setEvent(po);
         event.setDate(date);
        event.setReason(reason);
        event.setEventType(ProductOrderEventTypeEnum.OrderStatusChangedNotification);
        publish(event);

    }

    @Override
    public void publishOrderChangedNotification(ProductOrder po, String reason, Date date) {

        HubEvent event = new HubEvent();
        event.setDate(date);
        event.setReason(reason);
        event.setEvent(po);
        event.setEventType(ProductOrderEventTypeEnum.OrderValueChangeNotification);
        publish(event);

    }

    /**
     *
     * @param tt
     */
    @Override
    public void publishOrderRemoveNotification(ProductOrder po, String reason, Date date) {

        HubEvent event = new HubEvent();
        event.setEvent(po);
        
         event.setDate(date);
        event.setReason(reason);
        event.setEventType(ProductOrderEventTypeEnum.OrderRemoveNotification);
        publish(event);

    }

}
