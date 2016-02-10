package de.imi.odmtoolbox.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a Protocol from the ODM standard.
 */
public class ODMProtocol {

    List<ODMProtocolODMStudyEvent> studyEvents = new ArrayList<>();

    private ODMProtocol() {
    }

    public List<ODMProtocolODMStudyEvent> getStudyEvents() {
        return studyEvents;
    }

    public Set<ODMForm> getForms() {
        Set<ODMForm> forms = new HashSet<>();
        for (ODMProtocolODMStudyEvent studyEvent : getStudyEvents()) {
            for (ODMStudyEventODMForm form : studyEvent.getStudyEvent().getForms()) {
                forms.add(form.getForm());
            }
        }
        return forms;
    }

    public Set<ODMItem> getItems() {
        Set<ODMItem> items = new HashSet<>();
        for (ODMProtocolODMStudyEvent studyEvent : getStudyEvents()) {
            items.addAll(studyEvent.getStudyEvent().getItems());
        }
        return items;
    }

    public Integer getItemCount() {
        return getItems().size();
    }

    public Integer getCodeListItemCount() {
        Integer codelistItemCount = 0;
        for (ODMProtocolODMStudyEvent studyEvent : getStudyEvents()) {
            codelistItemCount += studyEvent.getStudyEvent().getCodeListItemCount();
        }
        return codelistItemCount;
    }

    public Set<ODMCodelist> getCodelists() {
        Set<ODMCodelist> codelists = new HashSet<>();
        for (ODMProtocolODMStudyEvent studyEvent : getStudyEvents()) {
            codelists.addAll(studyEvent.getStudyEvent().getCodelists());
        }
        return codelists;
    }

    public Integer getCodeListCount() {
        return getCodelists().size();
    }

    public Set<ODMItemGroup> getItemGroups() {
        Set<ODMItemGroup> itemGroups = new HashSet<>();
        for (ODMProtocolODMStudyEvent studyEvent : getStudyEvents()) {
            itemGroups.addAll(studyEvent.getStudyEvent().getItemGroups());
        }
        return itemGroups;
    }

    public Integer getItemGroupCount() {
        return getItemGroups().size();
    }

    public void addStudyEvent(ODMProtocolODMStudyEvent studyEvent) {
        this.studyEvents.add(studyEvent);
    }

    /**
     * Parses a list of {@link ODMProtocol ODMProtocols} from a given XML
     * nodelist.
     *
     * @param protocols XML nodelist of protocols from the ODM standard.
     * @param studyEvents Beforehand parsed {@link ODMStudyEvent ODMStudyEvents}
     * from the same XML document.
     * @return The list of parsed {@link ODMProtocol ODMProtocols}.
     */
    public static List<ODMProtocol> parseProtocols(NodeList protocols, List<ODMStudyEvent> studyEvents) {
        List<ODMProtocol> odmProtocols = new ArrayList<>();
        for (int i = 0; i < protocols.getLength(); i++) {
            Element protocol = (Element) protocols.item(i);
            ODMProtocol odmProtocol = new ODMProtocol();
            NodeList studyEventRef = protocol.getElementsByTagName("StudyEventRef");
            String studyEventOID = null;
            // Loop through attached study event references
            for (int j = 0; j < studyEventRef.getLength(); j++) {
                Element studyEventRefElement = (Element) studyEventRef.item(j);
                if (studyEventRef != null && studyEventRefElement.hasAttribute("StudyEventOID")) {
                    studyEventOID = studyEventRefElement.getAttribute("StudyEventOID");
                }
                // If the study event belongs to the current protocol then attach it
                if (studyEventOID != null) {
                    for (ODMStudyEvent studyEvent : studyEvents) {
                        if (studyEvent.getOID().equals(studyEventOID)) {
                            ODMProtocolODMStudyEvent protocolStudyEvent = new ODMProtocolODMStudyEvent(odmProtocol, studyEvent);
                            if (studyEventRefElement.hasAttribute("Mandatory")) {
                                if (studyEventRefElement.getAttribute("Mandatory").equalsIgnoreCase("yes")) {
                                    protocolStudyEvent.setMandatory(Boolean.TRUE);
                                } else {
                                    protocolStudyEvent.setMandatory(Boolean.FALSE);
                                }
                            }
                            if (studyEventRefElement.hasAttribute("OrderNumber")) {
                                protocolStudyEvent.setOrderNumber(Integer.parseInt(studyEventRefElement.getAttribute("OrderNumber")));
                            }
                            odmProtocol.addStudyEvent(protocolStudyEvent);
                            break;
                        }
                    }
                }
            }
            odmProtocols.add(odmProtocol);
        }
        return odmProtocols;
    }
}
