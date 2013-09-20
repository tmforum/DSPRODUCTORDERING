/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;

import java.io.Serializable;
import java.util.Arrays;

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
public class ProductOrderItem implements Serializable{
    
    String Id;
    OrderItemStateEnum state;
    String action;
    RefInfo productOffering;
    Product product;

   

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

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public OrderItemStateEnum getState() {
        return state;
    }

    public void setState(OrderItemStateEnum state) {
        this.state = state;
    }

    
  
}
