/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.hub.HubEvent;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class HubEventFacade extends tmf.org.dsmapi.ordering.service.AbstractFacade<HubEvent>{
    
    @PersistenceContext(unitName = "DSProductOrderingPU")
    private EntityManager em;
   

    
    /**
     *
     */
    public HubEventFacade() {
        super(HubEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
