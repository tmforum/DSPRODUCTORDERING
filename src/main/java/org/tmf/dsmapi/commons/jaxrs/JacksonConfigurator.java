package org.tmf.dsmapi.commons.jaxrs;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Provider
@Produces("application/json")
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {

    private ObjectMapper mapper = new ObjectMapper();
    
    public JacksonConfigurator() {
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return mapper;
    }

}