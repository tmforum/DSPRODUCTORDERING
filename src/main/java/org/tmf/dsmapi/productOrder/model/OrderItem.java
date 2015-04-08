//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.08 à 03:12:11 PM CEST 
//


package org.tmf.dsmapi.productOrder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * <p>Classe Java pour OrderItem complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="OrderItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://orange.com/api/productOrdering/tmf/v2/model/business}State" minOccurs="0"/>
 *         &lt;element name="billingAccount" type="{http://orange.com/api/productOrdering/tmf/v2/model/business}Reference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="appointment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productOffering" type="{http://orange.com/api/productOrdering/tmf/v2/model/business}ProductOffering" minOccurs="0"/>
 *         &lt;element name="product" type="{http://orange.com/api/productOrdering/tmf/v2/model/business}Product" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderItem", propOrder = {
    "id",
    "action",
    "state",
    "billingAccount",
    "appointment",
    "productOffering",
    "product"
})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity(name = "OrderItem")
@Table(name = "ORDER_ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
public class OrderItem
    implements Serializable
{

    private final static long serialVersionUID = 11L;
    protected String id;
    protected String action;
    protected State state;
    protected List<Reference> billingAccount;
    protected String appointment;
    protected ProductOffering productOffering;
    protected Product product;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "ID", length = 255)
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété action.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "ACTION_", length = 255)
    public String getAction() {
        return action;
    }

    /**
     * Définit la valeur de la propriété action.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Obtient la valeur de la propriété state.
     * 
     * @return
     *     possible object is
     *     {@link State }
     *     
     */
    @Basic
    @Column(name = "STATE_", length = 255)
    @Enumerated(EnumType.STRING)
    public State getState() {
        return state;
    }

    /**
     * Définit la valeur de la propriété state.
     * 
     * @param value
     *     allowed object is
     *     {@link State }
     *     
     */
    public void setState(State value) {
        this.state = value;
    }

    /**
     * Gets the value of the billingAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billingAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillingAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reference }
     * 
     * 
     */
    @OneToMany(targetEntity = Reference.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "BILLING_ACCOUNT_ORDER_ITEM_H_0")
    public List<Reference> getBillingAccount() {
        if (billingAccount == null) {
            billingAccount = new ArrayList<Reference>();
        }
        return this.billingAccount;
    }

    /**
     * 
     * 
     */
    public void setBillingAccount(List<Reference> billingAccount) {
        this.billingAccount = billingAccount;
    }

    /**
     * Obtient la valeur de la propriété appointment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "APPOINTMENT", length = 255)
    public String getAppointment() {
        return appointment;
    }

    /**
     * Définit la valeur de la propriété appointment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppointment(String value) {
        this.appointment = value;
    }

    /**
     * Obtient la valeur de la propriété productOffering.
     * 
     * @return
     *     possible object is
     *     {@link ProductOffering }
     *     
     */
    @ManyToOne(targetEntity = ProductOffering.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PRODUCT_OFFERING_ORDER_ITEM__0")
    public ProductOffering getProductOffering() {
        return productOffering;
    }

    /**
     * Définit la valeur de la propriété productOffering.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductOffering }
     *     
     */
    public void setProductOffering(ProductOffering value) {
        this.productOffering = value;
    }

    /**
     * Obtient la valeur de la propriété product.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    @ManyToOne(targetEntity = Product.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PRODUCT_ORDER_ITEM_HJID")
    public Product getProduct() {
        return product;
    }

    /**
     * Définit la valeur de la propriété product.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setProduct(Product value) {
        this.product = value;
    }

    /**
     * Obtient la valeur de la propriété hjid.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.codehaus.jackson.annotate.JsonIgnore
    public Long getHjid() {
        return hjid;
    }

    /**
     * Définit la valeur de la propriété hjid.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

}
