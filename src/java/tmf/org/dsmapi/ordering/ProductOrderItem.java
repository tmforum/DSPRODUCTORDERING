/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *"orderItems": [
        {
            "action": "add",
            "productOffering": {"id": "http://serverlocation:port/catalogManagement/productOffering/42"},
            "product": {"productCharacteristics": [
                {
                    "name": "Colour",
                    "value": "White"
                },
                {
                    "name": "Memory",
                    "value": "16"
                }
            ]}
        },
        {
            "action": "add",
            "productOffering": {"id": "http://serverlocation:port/catalogManagement/productOffering/43"},
            "product": {"productCharacteristics": [{
                "name": "anotherCharacteristic",
                "value": "itsValue"
            }]}
        }
    ]
 * @author pierregauthier
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class ProductOrderItem implements Serializable{
    
    String id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "PO_STATE")
    OrderItemStateEnum state;
    @Column(name = "PO_ACTION")
    String action;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column =
                @Column(name = "PRODUCT_OFFERING_NAME")),
        @AttributeOverride(name = "description", column =
                @Column(name = "PRODUCT_OFFERING_DESC")),
        @AttributeOverride(name = "href", column =
                @Column(name = "PRODUCT_OFFERING_ID"))
    })
    RefInfo productOffering;
    @Embedded
    Product product;

   
    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public RefInfo getProductOffering() {
        return productOffering;
    }

    public void setProductOffering(RefInfo productOffering) {
        this.productOffering = productOffering;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderItemStateEnum getState() {
        return state;
    }

    public void setState(OrderItemStateEnum state) {
        this.state = state;
    }

    
  
}
