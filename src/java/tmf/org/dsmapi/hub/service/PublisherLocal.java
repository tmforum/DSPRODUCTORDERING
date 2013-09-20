/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub.service;

import java.util.Date;
import javax.ejb.Local;
import tmf.org.dsmapi.ordering.ProductOrder;


/**
 *  private String reason;
 *  private Date date;
 * @author pierregauthier
 */
@Local
public interface PublisherLocal {

   void publish(Object event);

    public void publishOrderCreateNotification(ProductOrder po, String reason, Date date);

    public void publishOrderStatusChangedNotification(ProductOrder po, String reason, Date date);

    public void publishOrderChangedNotification(ProductOrder po, String reason, Date date);

    public void publishOrderRemoveNotification(ProductOrder po, String reason, Date date);

    
   
    
}
