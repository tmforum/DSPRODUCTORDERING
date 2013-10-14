/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;

import java.io.Serializable;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author pierregauthier
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class Product implements Serializable{
    
    
    private List<ProductCharacteristic> productCharacteristics;
    @Embedded
        @AttributeOverrides({
        @AttributeOverride(name = "name", column =
                @Column(name = "PRODUCT_SPEC_NAME")),
        @AttributeOverride(name = "description", column =
                @Column(name = "PRODUCT_SPEC_DESC")),
        @AttributeOverride(name = "id", column =
                @Column(name = "PRODUCT_SPEC_ID"))
    })
    private RefInfo productSpecification;
    
    /**
     * @return the productCharacteristics
     */
    public List<ProductCharacteristic> getProductCharacteristics() {
        return productCharacteristics;
    }

    /**
     * @param productCharacteristics the productCharacteristics to set
     */
    public void setProductCharacteristics(List<ProductCharacteristic> productCharacteristics) {
        this.productCharacteristics = productCharacteristics;
    }

    /**
     * @return the productSpecification
     */
    public RefInfo getProductSpecification() {
        return productSpecification;
    }

    /**
     * @param productSpecification the productSpecification to set
     */
    public void setProductSpecification(RefInfo productSpecification) {
        this.productSpecification = productSpecification;
    }
    
    
    
}
