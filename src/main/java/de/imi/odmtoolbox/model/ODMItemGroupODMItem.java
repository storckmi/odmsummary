package de.imi.odmtoolbox.model;

/**
 * This class represents a ItemRef from the ODM standard.
 *
 */
public class ODMItemGroupODMItem {

    private ODMItemGroup itemGroup;
    private ODMItem item;
    private Boolean mandatory;
    private Integer orderNumber;

    public ODMItemGroupODMItem(ODMItemGroup itemGroup, ODMItem item) {
        this.itemGroup = itemGroup;
        this.item = item;
    }

    public ODMItemGroup getItemGroup() {
        return itemGroup;
    }

    public ODMItem getItem() {
        return item;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
