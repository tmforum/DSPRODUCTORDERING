//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.08 à 03:12:11 PM CEST 
//


package org.tmf.dsmapi.productOrder.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tmf.dsmapi.productOrder.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Product_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "Product");
    private final static QName _ProductRelationship_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "ProductRelationship");
    private final static QName _ProductOffering_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "ProductOffering");
    private final static QName _OrderItem_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "OrderItem");
    private final static QName _ProductCharacteristic_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "ProductCharacteristic");
    private final static QName _ProductOrder_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "ProductOrder");
    private final static QName _Note_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "Note");
    private final static QName _Reference_QNAME = new QName("http://orange.com/api/productOrdering/tmf/v2/model/business", "Reference");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tmf.dsmapi.productOrder.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrderItem }
     * 
     */
    public OrderItem createOrderItem() {
        return new OrderItem();
    }

    /**
     * Create an instance of {@link Product }
     * 
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * Create an instance of {@link ProductOffering }
     * 
     */
    public ProductOffering createProductOffering() {
        return new ProductOffering();
    }

    /**
     * Create an instance of {@link ProductRelationship }
     * 
     */
    public ProductRelationship createProductRelationship() {
        return new ProductRelationship();
    }

    /**
     * Create an instance of {@link ProductOrder }
     * 
     */
    public ProductOrder createProductOrder() {
        return new ProductOrder();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link Note }
     * 
     */
    public Note createNote() {
        return new Note();
    }

    /**
     * Create an instance of {@link ProductCharacteristic }
     * 
     */
    public ProductCharacteristic createProductCharacteristic() {
        return new ProductCharacteristic();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Product }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "Product")
    public JAXBElement<Product> createProduct(Product value) {
        return new JAXBElement<Product>(_Product_QNAME, Product.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductRelationship }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "ProductRelationship")
    public JAXBElement<ProductRelationship> createProductRelationship(ProductRelationship value) {
        return new JAXBElement<ProductRelationship>(_ProductRelationship_QNAME, ProductRelationship.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductOffering }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "ProductOffering")
    public JAXBElement<ProductOffering> createProductOffering(ProductOffering value) {
        return new JAXBElement<ProductOffering>(_ProductOffering_QNAME, ProductOffering.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "OrderItem")
    public JAXBElement<OrderItem> createOrderItem(OrderItem value) {
        return new JAXBElement<OrderItem>(_OrderItem_QNAME, OrderItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductCharacteristic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "ProductCharacteristic")
    public JAXBElement<ProductCharacteristic> createProductCharacteristic(ProductCharacteristic value) {
        return new JAXBElement<ProductCharacteristic>(_ProductCharacteristic_QNAME, ProductCharacteristic.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "ProductOrder")
    public JAXBElement<ProductOrder> createProductOrder(ProductOrder value) {
        return new JAXBElement<ProductOrder>(_ProductOrder_QNAME, ProductOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Note }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "Note")
    public JAXBElement<Note> createNote(Note value) {
        return new JAXBElement<Note>(_Note_QNAME, Note.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reference }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/productOrdering/tmf/v2/model/business", name = "Reference")
    public JAXBElement<Reference> createReference(Reference value) {
        return new JAXBElement<Reference>(_Reference_QNAME, Reference.class, null, value);
    }

}
