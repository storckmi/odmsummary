package de.imi.odmtoolbox.model;

/**
 * This class represents a ItemGroupRef from the ODM standard.
 *
 */
public class ODMFormODMItemGroup {

    private ODMForm form;
    private ODMItemGroup itemGroup;
    private Boolean mandatory;
    private Integer orderNumber;

    public ODMFormODMItemGroup(ODMForm form, ODMItemGroup itemGroup) {
        this.form = form;
        this.itemGroup = itemGroup;
    }

    public ODMItemGroup getItemGroup() {
        return itemGroup;
    }

    public ODMForm getForm() {
        return form;
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
