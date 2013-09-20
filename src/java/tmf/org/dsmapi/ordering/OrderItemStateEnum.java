/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;


/**
 *
 * @author pierregauthier
 * NOT_STARTED”, “RUNNING”, “FAILED”, “COMPLETED”
 */
public enum OrderItemStateEnum {
    NotStarted, Running, Failed, Completed;  //; is optional

    public static OrderItemStateEnum fromString(String text) {
        if (text != null) {
            for (OrderItemStateEnum b : OrderItemStateEnum.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "OrderItemStateEnum{" + '}';
    }
}
