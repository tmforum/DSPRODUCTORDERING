/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Asynchronous
public class WorkflowListener implements WorkflowListenerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void update(ProductOrder po, ProductOrderEventTypeEnum state, String status) {
        
    }
        
      

}
