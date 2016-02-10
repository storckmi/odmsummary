package de.imi.odmtoolbox.model;

/**
 * This class represents a StudyEventRef from the ODM standard.
 *
 */
public class ODMStudyEventODMForm implements Comparable<ODMStudyEventODMForm> {

    private ODMStudyEvent studyEvent;
    private ODMForm form;
    private Boolean mandatory;
    private Integer orderNumber;

    public ODMStudyEventODMForm(ODMStudyEvent studyEvent, ODMForm form) {
        this.studyEvent = studyEvent;
        this.form = form;
    }

    public ODMStudyEvent getStudyEvent() {
        return studyEvent;
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

    @Override
    public int compareTo(ODMStudyEventODMForm o) {
        return orderNumber - o.getOrderNumber();
    }

}
