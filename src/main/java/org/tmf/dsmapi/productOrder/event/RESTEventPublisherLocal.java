package org.tmf.dsmapi.productOrder.event;

import javax.ejb.Local;
import org.tmf.dsmapi.productOrder.event.Event;
import org.tmf.dsmapi.hub.Hub;

@Local
public interface RESTEventPublisherLocal {

    public void publish(Hub hub, Event event);
    
}
