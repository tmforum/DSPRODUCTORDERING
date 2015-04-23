package org.tmf.dsmapi.productOrder;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.facade.AbstractFacade;
import org.tmf.dsmapi.commons.utils.BeanUtils;
import org.tmf.dsmapi.productOrder.event.EventPublisherLocal;
import org.tmf.dsmapi.productOrder.model.ProductOrder;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class ProductOrderFacade extends AbstractFacade<ProductOrder> {

    @PersistenceContext(unitName = "DSPRODUCTORDERING")
    private EntityManager em;
    @EJB
    EventPublisherLocal publisher;
    StateModelImpl stateModel = new StateModelImpl();

    public ProductOrderFacade() {
        super(ProductOrder.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(ProductOrder entity) throws BadUsageException {
        if (entity.getId() != null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, "While creating ProductOrder, id must be null");
        }

        super.create(entity);
    }

    public ProductOrder updateAttributs(long id, ProductOrder partialProduct) throws UnknownResourceException, BadUsageException {
        ProductOrder currentProduct = this.find(id);

        if (currentProduct == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialProduct, JsonNode.class);
        if (BeanUtils.verify(partialProduct, node, "state")) {
            
            
            stateModel.checkTransition(currentProduct.getState(), partialProduct.getState());
            System.out.println("About to publish statusChangedNotification "  );
            
            publisher.statusChangedNotification(currentProduct, new Date());
       } else {
            
            System.out.println("No State detectd "  );
            //throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "state" + " is not found");
        }

        partialProduct.setId(id);
        if (BeanUtils.patch(currentProduct, partialProduct, node)) {
            publisher.valueChangedNotification(currentProduct, new Date());
        }

        return currentProduct;
    }

}
