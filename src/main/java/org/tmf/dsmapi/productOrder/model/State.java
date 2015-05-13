//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.08 à 03:12:11 PM CEST 
//


package org.tmf.dsmapi.productOrder.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;


/**
 * <p>Classe Java pour State.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="State">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Acknowledged"/>
 *     &lt;enumeration value="InProgress"/>
 *     &lt;enumeration value="Cancelled"/>
 *     &lt;enumeration value="Completed"/>
 *     &lt;enumeration value="Rejected"/>
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Held"/>
 *     &lt;enumeration value="Failed"/>
 *     &lt;enumeration value="Partial"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "State")
@XmlEnum
public enum State {

    @XmlEnumValue("Initial")
    Initial("Initial"),
    @XmlEnumValue("Acknowledged")
    Acknowledged("Acknowledged"),
    @XmlEnumValue("InProgress")
    InProgress("InProgress"),
    @XmlEnumValue("Cancelled")
    Cancelled("Cancelled"),
    @XmlEnumValue("Completed")
    Completed("Completed"),
    @XmlEnumValue("Rejected")
    Rejected("Rejected"),
    @XmlEnumValue("Pending")
    Pending("Pending"),
    @XmlEnumValue("Held")
    Held("Held"),
    @XmlEnumValue("Failed")
    Failed("Failed"),
    @XmlEnumValue("Partial")
    Partial("Partial");
    private final String value;

    State(String v) {
        value = v;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static State fromValue(String v) {
        for (State c: State.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    @Override
    public String toString() {
        return value();
    }

}
