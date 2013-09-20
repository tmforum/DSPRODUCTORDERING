/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.ordering;

/**
 *
 * @author pierregauthier
 */
public enum OrderState {
    
    OPEN_RUNNING, CLOSED_COMPLETED, CLOSED_ABORTED_BYSERVER;
    
     public static OrderState fromString(String text) {
        if (text != null) {
            for (OrderState b : OrderState.values()) {
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

