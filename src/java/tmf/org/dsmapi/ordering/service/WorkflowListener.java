/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.hub.service.PublisherLocal;
import tmf.org.dsmapi.ordering.OrderState;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Asynchronous
public class WorkflowListener implements WorkflowListenerLocal {
    
    @EJB
    ProductOrderFacade poFacade;
    @EJB
    PublisherLocal publisher;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void update(ProductOrder po, ProductOrderEventTypeEnum eventType, String reason, OrderState status) {
        po.setStatus(status);
        poFacade.edit(po);
       
        switch(eventType) {
            case OrderCreateNotification:
                publisher.publishOrderCreateNotification(po, null, null);
            break;
            case OrderStatusChangedNotification:
                publisher.publishOrderStatusChangedNotification(po, null, null);
            break;
        }
    }
        
      

}
