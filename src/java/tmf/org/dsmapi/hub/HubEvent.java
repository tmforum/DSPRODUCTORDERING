/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import tmf.org.dsmapi.ordering.ProductOrder;


/**
 *
 * @author pierregauthier
 */
@XmlRootElement
public class HubEvent implements Serializable {

    private ProductOrder event; 
    private ProductOrderEventTypeEnum eventType;
    private String reason;
    private Date date;

    public ProductOrder getEvent() {
        return event;
    }

    public void setEvent(ProductOrder event) {
        this.event = event;
    }

    public ProductOrderEventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(ProductOrderEventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


  
}
