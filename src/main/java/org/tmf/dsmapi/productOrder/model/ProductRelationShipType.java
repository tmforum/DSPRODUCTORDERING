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
 * <p>Classe Java pour ProductRelationShipType.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductRelationShipType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="reliesOn"/>
 *     &lt;enumeration value="bundled"/>
 *     &lt;enumeration value="targets"/>
 *     &lt;enumeration value="isTargeted"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductRelationShipType")
@XmlEnum
public enum ProductRelationShipType {

    @XmlEnumValue("reliesOn")
    RELIES_ON("reliesOn"),
    @XmlEnumValue("bundled")
    BUNDLED("bundled"),
    @XmlEnumValue("targets")
    TARGETS("targets"),
    @XmlEnumValue("isTargeted")
    IS_TARGETED("isTargeted");
    private final String value;

    ProductRelationShipType(String v) {
        value = v;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static ProductRelationShipType fromValue(String v) {
        for (ProductRelationShipType c: ProductRelationShipType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
