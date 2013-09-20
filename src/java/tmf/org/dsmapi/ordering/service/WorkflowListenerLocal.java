/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import tmf.org.dsmapi.hub.ProductOrderEventTypeEnum;
import tmf.org.dsmapi.ordering.OrderState;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Local
public interface WorkflowListenerLocal {
    
  
    void update(ProductOrder po, ProductOrderEventTypeEnum state, String reason, OrderState status);
    
}
