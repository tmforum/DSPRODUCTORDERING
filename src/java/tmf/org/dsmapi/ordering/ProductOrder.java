/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * {"productOrder": { "id":
 * "http://serverlocation:port/orderManagement/productOrder/42", "externalID":
 * "NiceNameForTheConsumer_42", "description": "A wonderful 42 order for brand
 * new products", "type": "residential", "status": "OPEN.RUNNING", "orderDate":
 * "2013-07-23 08:16:39ZGMT+1", "completionDate": "2013-07-24 01:00:00ZGMT+1", "
 * requestedCompletionDate ": "2013-07-24 08:00:00ZGMT+1", "relatedParties": [ {
 * "role": "customer", "reference": "MSISDN-0033689770600" }, { "role":
 * "partner", "reference": "
 * http://serverlocation:port/partnerManagement/partner/42" } ], "orderItems": [
 * { "action": "add", "productOffering": {"id":
 * "http://serverlocation:port/catalogManagement/productOffering/42"},
 * "product": {"productCharacteristics": [ { "name": "Colour", "value": "White"
 * }, { "name": "Memory", "value": "16" } ]} }, { "action": "add",
 * "productOffering": {"id":
 * "http://serverlocation:port/catalogManagement/productOffering/43"},
 * "product": {"productCharacteristics": [{ "name": "anotherCharacteristic",
 * "value": "itsValue" }]} } ]
 *
 *
 * }}
 *
 * @author pierregauthier
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String externalID;
    private String description;
    private String type;
    @Enumerated(value = EnumType.STRING)
    private OrderState status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestedCompletionDate;
    @ElementCollection
    private List<RelatedParty> relatedParties;
    @ElementCollection
    private List<ProductOrderItem> orderItems;

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderState getStatus() {
        return status;
    }

    public void setStatus(OrderState status) {
        this.status = status;
    }

    

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getRequestedCompletionDate() {
        return requestedCompletionDate;
    }

    public void setRequestedCompletionDate(Date requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
    }

    public List<RelatedParty> getRelatedParties() {
        return relatedParties;
    }

    public void setRelatedParties(List<RelatedParty> relatedParties) {
        this.relatedParties = relatedParties;
    }

    public List<ProductOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ProductOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductOrder)) {
            return false;
        }
        ProductOrder other = (ProductOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tmf.org.dsmapi.ordering.ProductOrder[ id=" + id + " ]";
    }
}
