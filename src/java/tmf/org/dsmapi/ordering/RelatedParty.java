/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;

import java.io.Serializable;
import javax.persistence.Embeddable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author pierregauthier
 * "relatedParties": [
        {
            "role": "Originator",
            "reference": "/customer/1234"
        },
        {
            "role": "Owner",
            "reference": "/operator/1234"
        },
        {
            "role": "Reviser",
            "reference": "Roger Collins"
        }
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class RelatedParty implements Serializable {
    
    private String role;
    private String reference;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    
}
