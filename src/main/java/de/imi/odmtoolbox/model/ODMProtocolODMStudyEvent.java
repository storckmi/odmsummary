package de.imi.odmtoolbox.model;

/**
 * This class represents a StudyEventRef from the ODM standard.
 *
 */
public class ODMProtocolODMStudyEvent {

    private ODMProtocol protocol;
    private ODMStudyEvent studyEvent;
    private Boolean mandatory;
    private Integer orderNumber;

    public ODMProtocolODMStudyEvent(ODMProtocol protocol, ODMStudyEvent studyEvent) {
        this.protocol = protocol;
        this.studyEvent = studyEvent;
    }

    public ODMProtocol getProtocol() {
        return protocol;
    }

    public ODMStudyEvent getStudyEvent() {
        return studyEvent;
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
