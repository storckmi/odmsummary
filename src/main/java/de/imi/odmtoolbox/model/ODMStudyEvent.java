package de.imi.odmtoolbox.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a StudyEventDef from the ODM standard.
 * 
 */
public class ODMStudyEvent {

    private String oid;
    private String name;
    List<ODMStudyEventODMForm> forms = new ArrayList<>();

    private ODMStudyEvent(String oid, String name) {
        this.oid = oid;
        this.name = name;
    }

    public String getOID() {
        return oid;
    }

    public String getName() {
        return name;
    }

    public List<ODMStudyEventODMForm> getForms() {
        return forms;
    }

    public Set<ODMItem> getItems() {
        Set<ODMItem> items = new HashSet<>();
        for (ODMStudyEventODMForm form : getForms()) {
                items.addAll(form.getForm().getItems());
        }
        return items;
    }

    public Integer getItemCount() {
        return getItems().size();
    }

    public Integer getCodeListItemCount() {
        Integer codelistItemCount = 0;
        for (ODMCodelist codelist : getCodelists()) {
            codelistItemCount += codelist.getCodeListItemCount();
        }
        return codelistItemCount;
    }

    public Set<ODMCodelist> getCodelists(){
        Set<ODMCodelist> codelists =  new HashSet<>();
        for (ODMStudyEventODMForm form : getForms()) {
            codelists.addAll(form.getForm().getCodelists());
        }
        return codelists;
    }
    
    public Integer getCodeListCount() {
        return getCodelists().size();
    }
    
    public Set<ODMItemGroup> getItemGroups(){
        Set<ODMItemGroup> itemGroups =  new HashSet<>();
        for (ODMStudyEventODMForm forms : getForms()) {
            for (ODMFormODMItemGroup itemGroup : forms.getForm().getItemGroups()) {
                itemGroups.add(itemGroup.getItemGroup());
            }
        }
        return itemGroups;
    }
    public Integer getItemGroupCount() {
        return getItemGroups().size();
    }

    public void addForm(ODMStudyEventODMForm form) {
        this.forms.add(form);
    }
    
    /**
     * Parses a list of {@link ODMStudyEvent ODMStudyEvents} from a given XML nodelist.
     * 
     * @param studyEvents XML nodelist of study events from the ODM standard.
     * @param forms Beforehand parsed {@link ODMForm ODMForms} from the same XML document.
     * 
     * @return The list of parsed {@link ODMStudyEvent ODMStudyEvents}.
     */
    public static List<ODMStudyEvent> parseStudyEvents(NodeList studyEvents, List<ODMForm> forms) {
        List<ODMStudyEvent> odmStudyEvents = new ArrayList<>();
        for (int i = 0; i < studyEvents.getLength(); i++) {
            Element studyEvent = (Element) studyEvents.item(i);
            String studyEventOID = studyEvent.getAttribute("OID");
            String studyEventName = studyEvent.getAttribute("Name");
            ODMStudyEvent odmStudyEvent = new ODMStudyEvent(studyEventOID, studyEventName);
            NodeList formRef = studyEvent.getElementsByTagName("FormRef");
            String formOID = null;
            // Loop through attached form references
            for (int j = 0; j < formRef.getLength(); j++) {
                Element formRefElement = (Element) formRef.item(j);
                if (formRef != null && formRefElement.hasAttribute("FormOID")) {
                    formOID = formRefElement.getAttribute("FormOID");
                }
                // If the form belongs to the current study event then attach it
                if (formOID != null) {
                    for (ODMForm form : forms) {
                        if (form.getOID().equals(formOID)) {
                            ODMStudyEventODMForm studyEventForm = new ODMStudyEventODMForm(odmStudyEvent, form);
                            if (formRefElement.hasAttribute("Mandatory")) {
                                if (formRefElement.getAttribute("Mandatory").equalsIgnoreCase("yes")) {
                                    studyEventForm.setMandatory(Boolean.TRUE);
                                } else {
                                    studyEventForm.setMandatory(Boolean.FALSE);
                                }
                            }
                            if (formRefElement.hasAttribute("OrderNumber")) {
                                studyEventForm.setOrderNumber(Integer.parseInt(formRefElement.getAttribute("OrderNumber")));
                            }
                            odmStudyEvent.addForm(studyEventForm);
                            break;
                        }
                    }
                }
            }
            odmStudyEvents.add(odmStudyEvent);
        }
        return odmStudyEvents;
    }
}
