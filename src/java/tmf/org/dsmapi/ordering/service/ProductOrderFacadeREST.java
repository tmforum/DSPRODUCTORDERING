/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.hub.service.PublisherLocal;
import tmf.org.dsmapi.ordering.OrderItemStateEnum;
import tmf.org.dsmapi.ordering.OrderState;
import tmf.org.dsmapi.ordering.Product;
import tmf.org.dsmapi.ordering.ProductCharacteristic;
import tmf.org.dsmapi.ordering.ProductOrder;
import tmf.org.dsmapi.ordering.ProductOrderItem;
import tmf.org.dsmapi.ordering.RefInfo;
import tmf.org.dsmapi.ordering.RelatedParty;
import tmf.org.dsmapi.ordering.Report;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("productOrder")
public class ProductOrderFacadeREST {

    @EJB
    WorkFlowLocalLocal workflow;
    @EJB
    PublisherLocal publisher;
    @EJB
    ProductOrderFacade manager;

    public ProductOrderFacadeREST() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(ProductOrder entity) {
        entity.setId(null);
        entity.setStatus(OrderState.OPEN_RUNNING);
        manager.create(entity);
        workflow.execute(entity);
        Response response = Response.ok(entity).build();
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response edit(@PathParam("id") String id, ProductOrder entity) {
        Response response = null;
        ProductOrder order = manager.find(id);
        if (order != null) {
            // 200
            entity.setId(id);
            manager.edit(entity);
            response = Response.ok(entity).build();
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @GET
    @Produces({"application/json"})
    public Response findByCriteriaWithFields(@Context UriInfo info) {

        // search criteria
        MultivaluedMap<String, String> criteria = FacadeRestUtil.parseFields(info);
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(criteria);

        Set<ProductOrder> resultList = findByCriteria(criteria);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(FacadeRestUtil.ID_FIELD);
            List<ObjectNode> nodeList = FacadeRestUtil.createNodeListViewWithFields(resultList, fieldSet);
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    // return Set of unique elements to avoid List with same elements in case of join
    private Set<ProductOrder> findByCriteria(MultivaluedMap<String, String> criteria) {
        List<ProductOrder> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = manager.findByCriteria(criteria, ProductOrder.class);
        } else {
            resultList = manager.findAll();
        }
        if (resultList == null) {
            return new LinkedHashSet<ProductOrder>();
        } else {
            return new LinkedHashSet<ProductOrder>(resultList);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response findById(@PathParam("id") String id, @Context UriInfo info) {
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(info.getQueryParameters());

        ProductOrder p = manager.find(id);
        Response response;
        if (p != null) {
            // 200
            if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
                response = Response.ok(p).build();
            } else {
                fieldSet.add(FacadeRestUtil.ID_FIELD);
                ObjectNode node = FacadeRestUtil.createNodeViewWithFields(p, fieldSet);
                response = Response.ok(node).build();
            }
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @DELETE
    @Path("admin/{id}")
    public void remove(@PathParam("id") String id) {
        manager.remove(manager.find(id));
    }

    @GET
    @Path("admin/count")
    @Produces({"application/json"})
    public Report count() {
        return new Report(manager.count());
    }

    @POST
    @Path("admin")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createList(LinkedList<ProductOrder> entities) {
        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = manager.count();
        int affectedRows;

        affectedRows = manager.create(entities);

        Report stat = new Report(manager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @DELETE
    @Path("admin")
    public Report deleteAll() {

        int previousRows = manager.count();
        manager.removeAll();
        int currentRows = manager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @GET
    @Path("proto")
    @Produces({"application/json"})
    public ProductOrder proto() {
        ProductOrder po = new ProductOrder();

        po.setId("1");
        po.setCompletionDate(new Date());
        po.setDescription("description");
        po.setExternalID("externalId");
        po.setOrderDate(new Date());

        RelatedParty party = new RelatedParty();
        party.setReference("ref");
        party.setRole("role");
        po.setRelatedParties(Arrays.asList(party));

        ProductOrderItem item = new ProductOrderItem();
        item.setAction("action");
        item.setId("item1");

        Product product = new Product();

        ProductCharacteristic productCharacteristic = new ProductCharacteristic();
        productCharacteristic.setName("name");
        productCharacteristic.setValue("value");
        product.setProductCharacteristics(Arrays.asList(productCharacteristic));

        RefInfo refInfo = new RefInfo();
        refInfo.setDescription("description");
        refInfo.setId("id");
        refInfo.setName("name");
        product.setProductSpecification(refInfo);

        item.setProduct(product);
        RefInfo productOffering = refInfo;
        item.setProductOffering(productOffering);
        item.setState(OrderItemStateEnum.Running);

        ProductOrderItem item2 = new ProductOrderItem();
        item2.setAction("action");
        item2.setId("item2");
        item2.setProduct(product);
        item2.setProductOffering(productOffering);
        item2.setState(OrderItemStateEnum.NotStarted);

        po.setOrderItems(Arrays.asList(item, item2));

        po.setRequestedCompletionDate(new Date());
        po.setStatus(OrderState.OPEN_RUNNING);
        po.setType("type");

        return po;
    }
}
