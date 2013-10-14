/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@XmlRootElement
@Entity
@JsonPropertyOrder(value = {"event", "reason", "date", "eventType"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HubEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private String id;
    private ProductOrder productOrder;
    private String reason;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEvent;
    @Enumerated(value = EnumType.STRING)
    private ProductOrderEventTypeEnum eventType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductOrder getEvent() {
        return productOrder;
    }

    public void setEvent(ProductOrder event) {
        this.productOrder = event;
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
        return dateEvent;
    }

    public void setDate(Date date) {
        this.dateEvent = date;
    }

    @Override
    public String toString() {
        return "HubEvent{" + "id=" + id + ", productOrder=" + productOrder + ", eventType=" + eventType + ", reason=" + reason + ", dateEvent=" + dateEvent + '}';
    }
}
