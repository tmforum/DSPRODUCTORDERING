package org.tmf.dsmapi.productOrder;

import java.util.Date;
import java.util.List;
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
import org.tmf.dsmapi.commons.utils.TMFDate;
import org.tmf.dsmapi.productOrder.event.EventPublisherLocal;
import org.tmf.dsmapi.productOrder.model.Note;
import org.tmf.dsmapi.productOrder.model.OrderItem;
import org.tmf.dsmapi.productOrder.model.ProductOrder;
import org.tmf.dsmapi.productOrder.model.Reference;
import org.tmf.dsmapi.productOrder.model.State;

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

    public void checkCreation(ProductOrder newProductOrder) throws BadUsageException, UnknownResourceException {

        if (newProductOrder.getId() != null) {
            if (this.find(newProductOrder.getId()) != null) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                        "Duplicate Exception, Product with same id :" + newProductOrder.getId() + " alreay exists");
            }
        }

        if (null == newProductOrder.getState()) {
            newProductOrder.setState(State.Acknowledged);
        } else {
            if (!newProductOrder.getState().name().equalsIgnoreCase(State.Acknowledged.name())) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_FLOW_TRANSITION, "state " + newProductOrder.getState().value() + " is not the first state, attempt : " + State.Acknowledged.value());
            }
        }

        if (null == newProductOrder.getPriority()
                || newProductOrder.getPriority().isEmpty()) {
            newProductOrder.setPriority("4");
        }

        if (null == newProductOrder.getCategory()
                || newProductOrder.getCategory().isEmpty()) {
            newProductOrder.setCategory("uncategorized");
        }

        if (null == newProductOrder.getRequestedStartDate()) {
            newProductOrder.setRequestedStartDate(new Date());
        }

        if (null == newProductOrder.getRequestedCompletionDate()) {
            newProductOrder.setRequestedCompletionDate(new Date());
        }

        if (null == newProductOrder.getRequestedCompletionDate()) {
            newProductOrder.setRequestedCompletionDate(new Date());
        }

//      POST Mandatory attributes within product Order
        if (null != newProductOrder.getNote()
                && !newProductOrder.getNote().isEmpty()) {
            List<Note> l_note = newProductOrder.getNote();
            for (Note note : l_note) {
                if (null == note.getText()
                        || note.getText().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "note.text is mandatory");
                }
            }
        }

        if (null != newProductOrder.getRelatedParty()
                && !newProductOrder.getRelatedParty().isEmpty()) {
            List<Reference> l_relatedParty = newProductOrder.getRelatedParty();
            for (Reference relatedParty : l_relatedParty) {
                if ((null == relatedParty.getId() || relatedParty.getId().isEmpty())
                        && (null == relatedParty.getHref() || relatedParty.getHref().isEmpty())
                        && (null == relatedParty.getName() || relatedParty.getName().isEmpty())) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                            "relatedParty . id AND/OR href AND/OR name are mandatory");
                }
                if (null == relatedParty.getRole()
                        || relatedParty.getRole().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                            "relatedParty.role is mandatory");
                }
            }
        } else {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty is mandatory");
        }

//      POST Mandatory attributes within product Order item
        if (null != newProductOrder.getOrderItem()
                && !newProductOrder.getOrderItem().isEmpty()) {
            List<OrderItem> l_orderItem = newProductOrder.getOrderItem();
            for (OrderItem orderItem : l_orderItem) {
                if (null == orderItem.getState()) {
                    orderItem.setState(State.Acknowledged);
                } else {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_FLOW_TRANSITION, "orderItem.state " + orderItem.getState().value() + " is not the first state, attempt : " + State.Acknowledged.value());
                }
                if (null == orderItem.getId()
                        || orderItem.getId().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem.id  is mandatory");
                }
                if (null == orderItem.getBillingAccount()
                        || orderItem.getBillingAccount().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem.billingAccount is mandatory");
                }
                if (null == orderItem.getAction()
                        || orderItem.getAction().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem.action is mandatory");
                }
                if (null == orderItem.getProduct()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem.product is mandatory");
                }
                if (orderItem.getAction().equalsIgnoreCase("add")) {
                    if (null == orderItem.getProductOffering()) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem.productOffering is mandatory if action is add");
                    } else {
                        if ((null == orderItem.getProductOffering().getId() || orderItem.getProductOffering().getId().isEmpty())
                                && (null == orderItem.getProductOffering().getHref() || orderItem.getProductOffering().getHref().isEmpty())) {
                            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                                    "orderItem.productOffering id AND/OR href are mandatory");
                        }
                    }
                    if (null != orderItem.getProduct()) {
                        if (null == orderItem.getProduct().getProductCharacteristic()
                                || orderItem.getProduct().getProductCharacteristic().isEmpty()) {
                            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem.product.productCharacteristic is mandatory if action is add");
                        }
                    }
                } else if (orderItem.getAction().equalsIgnoreCase("modify")
                        || orderItem.getAction().equalsIgnoreCase("delete")) {
                    if (null != orderItem.getProduct()) {
                        if ((null == orderItem.getProduct().getId() || orderItem.getProduct().getId().isEmpty())
                                && (null == orderItem.getProduct().getHref() || orderItem.getProduct().getHref().isEmpty())) {
                            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                                    "orderItem.product id AND/OR href are mandatory  if action is 'modify' or 'delete'");
                        }
                    } else {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                                "orderItem.product is mandatory if action is 'modify' or 'delete'");
                    }
                }
                if (null != orderItem.getProduct().getPlace()) {
                    if (null == orderItem.getProduct().getPlace().getRole()
                            || orderItem.getProduct().getPlace().getRole().isEmpty()) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                                "orderItem.product.place.role is mandatory");
                    }
                    if ((null == orderItem.getProduct().getPlace().getId() || orderItem.getProduct().getPlace().getId().isEmpty())
                            && (null == orderItem.getProduct().getPlace().getHref() || orderItem.getProduct().getPlace().getHref().isEmpty())) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                                "orderItem.product.place id AND/OR href are mandatory");
                    }
                }
            }
        } else {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "orderItem is mandatory");
        }

    }

    public ProductOrder patchAttributs(long id, ProductOrder partialProduct) throws UnknownResourceException, BadUsageException {
        ProductOrder currentProduct = this.find(id);

        if (currentProduct == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        if (null != partialProduct.getId()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "id is not patchable");
        }

        if (null != partialProduct.getHref()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "href is not patchable");
        }

        if (null != partialProduct.getExternalId()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "externalId not patchable");
        }

        if (null != partialProduct.getOrderDate()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "orderDate not patchable");
        }

        if (null != partialProduct.getCompletionDate()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "completionDate not patchable");
        }

        if (null != partialProduct.getState()) {
            if (partialProduct.getState().name().equalsIgnoreCase(State.Pending.name())) {
                if (null != currentProduct.getOrderItem()
                        && !currentProduct.getOrderItem().isEmpty()) {
                    List<OrderItem> l_orderItem = currentProduct.getOrderItem();
                    for (OrderItem orderItem : l_orderItem) {
                        if (null != orderItem.getState() || orderItem.getState().name().equalsIgnoreCase(State.InProgress.name())) {
                            orderItem.setState(State.Pending);
                        }
                    }
                }
            }
            if (partialProduct.getState().name().equalsIgnoreCase(State.Held.name())) {
                if (null != currentProduct.getOrderItem()
                        && !currentProduct.getOrderItem().isEmpty()) {
                    List<OrderItem> l_orderItem = currentProduct.getOrderItem();
                    for (OrderItem orderItem : l_orderItem) {
                        if (null != orderItem.getState() || orderItem.getState().name().equalsIgnoreCase(State.InProgress.name())) {
                            orderItem.setState(State.Held);
                        }
                    }
                }
            }
            if (partialProduct.getState().name().equalsIgnoreCase(State.InProgress.name())) {
                if (null != currentProduct.getOrderItem()
                        && !currentProduct.getOrderItem().isEmpty()) {
                    List<OrderItem> l_orderItem = currentProduct.getOrderItem();
                    for (OrderItem orderItem : l_orderItem) {
//                        if (null != orderItem.getState() || orderItem.getState().name().equalsIgnoreCase(State.InProgress.name())) {
                        orderItem.setState(State.InProgress);
//                        }
                    }
                }
            }

        } else {

        }

        if (null != partialProduct.getOrderItem()) {
            if (null != partialProduct.getOrderItem()
                    && !partialProduct.getOrderItem().isEmpty()) {
                List<OrderItem> l_orderItem = partialProduct.getOrderItem();
                for (OrderItem orderItem : l_orderItem) {
                    if (null != orderItem.getId()) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "orderItem.id not patchable");
                    }
                    if (null != orderItem.getAction()) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "orderItem.action not patchable");
                    }
                    if (null != partialProduct.getState()) {
                        if (null != orderItem.getBillingAccount()
                                && !orderItem.getBillingAccount().isEmpty()) {
                            if (!partialProduct.getState().name().equalsIgnoreCase(State.Acknowledged.name())) {
                                throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR,
                                        "orderItem.billingAccount is patchable only with order state : " + State.Acknowledged.name());
                            }
                        }
                        if (null != orderItem.getProduct()) {
                            if (!partialProduct.getState().name().equalsIgnoreCase(State.Acknowledged.name())
                                    && !partialProduct.getState().name().equalsIgnoreCase(State.Pending.name())) {
                                throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR,
                                        "orderItem.product is patchable only with order state : " + State.Acknowledged.name() + " or " + State.Pending.name());
                            }
                        } else {
                            if (null != orderItem.getProduct().getPlace()) {
                                if (!partialProduct.getState().name().equalsIgnoreCase(State.Acknowledged.name())
                                        && !partialProduct.getState().name().equalsIgnoreCase(State.Pending.name())) {
                                    throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR,
                                            "orderItem.product.place is patchable only with order state : " + State.Acknowledged.name() + " or " + State.Pending.name());
                                }
                            }
                        }
                        if (null != orderItem.getAppointment()
                                && !orderItem.getAppointment().isEmpty()) {
                            if (!partialProduct.getState().name().equalsIgnoreCase(State.Acknowledged.name())
                                    && !partialProduct.getState().name().equalsIgnoreCase(State.Pending.name())) {
                                throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR,
                                        "orderItem.appointment is patchable only with order state : " + State.Acknowledged.name() + " or " + State.Pending.name());
                            }
                        }
                    } else {
                        if (!currentProduct.getState().name().equalsIgnoreCase(State.Acknowledged.name())
                                && !currentProduct.getState().name().equalsIgnoreCase(State.Pending.name())) {
                            //TODO prendre le state du currentProduct
                            if (null != orderItem.getBillingAccount()
                                    || null != orderItem.getProduct()
                                    || null != orderItem.getAppointment()) {
                                throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR,
                                        "orderItem billingAccount, product, appointment are patchable "
                                        + "only with order state : " + State.Acknowledged.name() + " or " + State.Pending.name());

                            }
                            if (orderItem.getState().name().equalsIgnoreCase(State.Pending.name())
                                    || orderItem.getState().name().equalsIgnoreCase(State.Held.name())) {
                                if (!partialProduct.getState().name().equalsIgnoreCase(State.Pending.name())
                                        && !partialProduct.getState().name().equalsIgnoreCase(State.Held.name())) {
                                    throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR,
                                            "order.state must be equal : " + State.Pending.name() + " or " + State.Held.name()
                                            + "if orderItem state equal" + State.Pending.name() + " or " + State.Held.name());
                                }
                            }
                        }
                    }
                }
            }
        }

        if (null != partialProduct.getState()) {
            stateModel.checkTransition(currentProduct.getState(), partialProduct.getState());
            System.out.println("About to publish statusChangedNotification ");

            publisher.stateChangeNotification(currentProduct, new Date());
        } else {
            System.out.println("No State detectd ");
            //throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "state" + " is not found");
        }

        partialProduct.setId(id);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialProduct, JsonNode.class);
        if (BeanUtils.patch(currentProduct, partialProduct, node)) {
            publisher.valueChangeNotification(currentProduct, new Date());
        }
        return currentProduct;
    }
}
