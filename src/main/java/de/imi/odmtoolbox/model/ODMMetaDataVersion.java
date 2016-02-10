package de.imi.odmtoolbox.model;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a MetaDataversion from the ODM standard.
 *
 */
public class ODMMetaDataVersion {

    private String OID;
    private String name;
    private List<ODMProtocol> protocols;
    private List<ODMForm> forms;
    private List<ODMStudyEvent> studyEvents;

    public ODMMetaDataVersion(String OID, String name, List<ODMProtocol> protocols, List<ODMForm> forms, List<ODMStudyEvent> studyEvents) {
        this.OID = OID;
        this.name = name;
        this.protocols = protocols;
        this.forms = forms;
        this.studyEvents = studyEvents;
    }

    public String getOID() {
        return OID;
    }

    public String getName() {
        return name;
    }

    public List<ODMProtocol> getProtocols() {
        return protocols;
    }

    public Set<ODMForm> getForms() {
        Set<ODMForm> forms = new HashSet<>();
        for (ODMProtocol protocol : getProtocols()) {
            forms.addAll(protocol.getForms());
        }
        return forms;
    }

    public Set<ODMItem> getItems() {
        Set<ODMItem> items = new HashSet<>();
        for (ODMProtocol protocol : getProtocols()) {
            items.addAll(protocol.getItems());
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

    public Set<ODMCodelist> getCodelists() {
        Set<ODMCodelist> codelists = new HashSet<>();
        for (ODMProtocol protocol : getProtocols()) {
            codelists.addAll(protocol.getCodelists());
        }
        return codelists;
    }

    public Integer getCodeListCount() {
        return getCodelists().size();
    }

    public Set<ODMItemGroup> getItemGroups() {
        Set<ODMItemGroup> itemGroups = new HashSet<>();
        for (ODMProtocol protocol : getProtocols()) {
            itemGroups.addAll(protocol.getItemGroups());
        }
        return itemGroups;
    }

    public Integer getItemGroupCount() {
        return getItemGroups().size();
    }

    /**
     * Parses a list of {@link ODMMetaDataVersion ODMMetaDataVersions} from a
     * given XML nodelist.
     *
     * @param metaDataVersions XML nodelist of meta-data versions from the ODM
     * standard.
     * @param study Beforehand parsed {@link ODMStudy} from the same XML
     * document.
     * @param umlsCodeDao The data access object for UMLS Codes, which are saved
     * to a database for further use.
     *
     * @return The list of parsed
     * {@link ODMMetaDataVersion ODMMetaDataVersions}.
     */
    public static List<ODMMetaDataVersion> parseMetaDataVersions(NodeList metaDataVersions, ODMStudy study, UMLSCodeDao umlsCodeDao) {
        List<ODMMetaDataVersion> odmMetaDataVersions = new ArrayList<>();
        for (int i = 0; i < metaDataVersions.getLength(); i++) {
            Element metaDataVersionNode = (Element) metaDataVersions.item(i);
            // Parse all Codelists from this meta data version
            List<ODMCodelist> codelists = ODMCodelist.parseCodelists(metaDataVersionNode.getElementsByTagName("CodeList"), umlsCodeDao);
            // Parse all items from this meta data version
            List<ODMItem> items = ODMItem.parseItems(metaDataVersionNode.getElementsByTagName("ItemDef"), codelists, umlsCodeDao);
            // Parse all item groups, given all items, which will be assigned to the appropriate item groups
            List<ODMItemGroup> itemGroups = ODMItemGroup.parseItemGroups(metaDataVersionNode.getElementsByTagName("ItemGroupDef"), items);
            // Parse all forms, given all item groups, which will be assigned to the appropriate forms
            List<ODMForm> forms = ODMForm.parseForms(metaDataVersionNode.getElementsByTagName("FormDef"), itemGroups, study);
            // Parse all study events, given all forms, which will be assigned to the appropriate study events
            List<ODMStudyEvent> studyEvents = ODMStudyEvent.parseStudyEvents(metaDataVersionNode.getElementsByTagName("StudyEventDef"), forms);
            // Parse all protocols, given all study events, which will be assigned to the appropriate protocols
            List<ODMProtocol> protocols = ODMProtocol.parseProtocols(metaDataVersionNode.getElementsByTagName("Protocol"), studyEvents);
            String metaDataVersionOID = metaDataVersionNode.getAttribute("OID");
            String metaDataVersionName = metaDataVersionNode.getAttribute("Name");
            odmMetaDataVersions.add(new ODMMetaDataVersion(metaDataVersionOID, metaDataVersionName, protocols, forms, studyEvents));
        }
        return odmMetaDataVersions;
    }
}
