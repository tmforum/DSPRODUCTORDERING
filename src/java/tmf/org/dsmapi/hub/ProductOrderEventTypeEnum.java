/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.hub;

/**
 *
 * @author pierregauthier
 */
public enum ProductOrderEventTypeEnum {

    OrderCreateNotification("OrderCreateNotification"),
    OrderStatusChangedNotification("OrderStatusChangedNotification"),
    OrderValueChangeNotification("OrderValueChangeNotification"),
    OrderRemoveNotification("OrderRemoveNotification");
    
    private String text;

    ProductOrderEventTypeEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static tmf.org.dsmapi.hub.ProductOrderEventTypeEnum fromString(String text) {
        if (text != null) {
            for (ProductOrderEventTypeEnum b : ProductOrderEventTypeEnum.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}