package org.tmf.dsmapi.productOrder.event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

@Stateless
public class EventFacade extends AbstractFacade<Event>{
    
    @PersistenceContext(unitName = "DSPRODUCTORDERING")
    private EntityManager em;
    
    /**
     *
     */
    public EventFacade() {
        super(Event.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
