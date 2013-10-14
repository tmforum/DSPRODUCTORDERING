/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub.service;

import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MultivaluedMap;
import tmf.org.dsmapi.hub.Hub;
import tmf.org.dsmapi.hub.HubEvent;
import tmf.org.dsmapi.ordering.service.FacadeRestUtil;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class RESTEventPublisher implements RESTEventPublisherLocal {

    @EJB
    HubEventFacade manager;

    @Override
    @Asynchronous
    public void publish(Hub hub, Object event) {

        PostEventClient client = new PostEventClient(hub.getCallback());

        String query = hub.getQuery();
        if (query != null && query.length() > 0) {
            MultivaluedMap<String, String> queryMap = FacadeRestUtil.parseFields(query);
            HubEvent hubEvent = (HubEvent) event;
            queryMap.putSingle("id", hubEvent.getId());
            List results = manager.findByCriteria(queryMap, HubEvent.class);
            if (results != null && !results.isEmpty()) {
                client.publishEvent(event);
            }
        } else {
            client.publishEvent(event);
        }

        System.out.println("Sending Event");
    }
}
