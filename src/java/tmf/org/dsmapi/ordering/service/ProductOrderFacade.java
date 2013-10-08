/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.ordering.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class ProductOrderFacade extends AbstractFacade<ProductOrder> {

    @PersistenceContext(unitName = "DSProductOrderingPU")
    private EntityManager em;

    public ProductOrderFacade() {
        super(ProductOrder.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
