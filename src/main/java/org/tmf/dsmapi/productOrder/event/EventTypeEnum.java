/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.productOrder.event;

public enum EventTypeEnum {

    orderCreationNotification("orderCreationNotification"),
    orderInformationRequiredNotification("orderInformationRequiredNotification"),
    orderRemoveNotification("orderRemoveNotification"),
    orderValueChangeNotification("orderValueChangeNotification"),
    orderStateChangeNotification("orderStateChangeNotification");

    private String text;

    EventTypeEnum(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return this.text;
    }

    /**
     *
     * @param text
     * @return
     */
    public static org.tmf.dsmapi.productOrder.event.EventTypeEnum fromString(String text) {
        if (text != null) {
            for (EventTypeEnum b : EventTypeEnum.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}