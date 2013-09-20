/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Asynchronous
public class WorkFlowLocal implements WorkFlowLocalLocal {
    
    @EJB
    WorkflowListenerLocal listener;

   
    public void execute(ProductOrder po)  {
        try {
            System.out.println("Excuting order");
            String status = null;
            listener.update(po, ProductOrderEventTypeEnum.OrderCreateNotification, status);
            Thread.sleep(3000);
            listener.update(po, ProductOrderEventTypeEnum.OrderStatusChangedNotification, status);
            System.out.println("Order Completed");
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkFlowLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
