package de.imi.odmtoolbox.model;

import de.imi.odmtoolbox.comparator.FormPerStudyComparator;
import de.imi.odmtoolbox.dao.UMLSCodeDao;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a Study from the ODM standard.
 *
 */
public class ODMStudy {

    private String OID;
    private String name;
    private String description;
    private String protocolName;
    private List<ODMMetaDataVersion> metaDataVersions;

    /**
     *
     * @param OID Identifier for this study within an ODM document.
     * @param name The short external name for the study.
     * @param description The free-text description of the study.
     * @param protocolName The sponsor's internal name for the protocol.
     */
    private ODMStudy(String OID, String name, String description, String protocolName) {
        this.OID = OID;
        this.name = name;
        this.description = description;
        this.protocolName = protocolName;
    }

    /**
     * @return The identifier for this study within an ODM document.
     */
    public String getOID() {
        return OID;
    }

    /**
     * @return The short external name for the study.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
    /**
     * @return The free-text description of the study.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return The sponsor's internal name for the protocol.
     */
    public String getProtocolName() {
        return protocolName;
    }

    public List<ODMMetaDataVersion> getMetaDataVersions() {
        return metaDataVersions;
    }

    public void setMetaDataVersions(List<ODMMetaDataVersion> metaDataVersions) {
        this.metaDataVersions = metaDataVersions;
    }

    public TreeSet<ODMForm> getForms() {
        Comparator<ODMForm> formComparator = new FormPerStudyComparator();
        TreeSet<ODMForm> forms = new TreeSet<>(formComparator);
        for (ODMMetaDataVersion metaDataVersion : getMetaDataVersions()) {
            forms.addAll(metaDataVersion.getForms());
        }
        return forms;
    }

    public Set<ODMItem> getItems() {
        Set<ODMItem> items = new HashSet<>();
        for (ODMMetaDataVersion metaDataVersion : getMetaDataVersions()) {
            items.addAll(metaDataVersion.getItems());
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
        for (ODMMetaDataVersion metaDataVersion : getMetaDataVersions()) {
            codelists.addAll(metaDataVersion.getCodelists());
        }
        return codelists;
    }

    public Integer getCodeListCount() {
        return getCodelists().size();
    }

    public Set<ODMItemGroup> getItemGroups() {
        Set<ODMItemGroup> itemGroups = new HashSet<>();
        for (ODMMetaDataVersion metaDataVersion : getMetaDataVersions()) {
            itemGroups.addAll(metaDataVersion.getItemGroups());
        }
        return itemGroups;
    }

    public Integer getItemGroupCount() {
        return getItemGroups().size();
    }
    
    /**
     * Parses a {@link ODMStudy} from the given XML document.
     * 
     * @param document The ODM document, which will be parsed to an ODMStudy.
     * @param umlsCodeDao The data access object for UMLS Codes, which are
     * saved to a database for further use.
     * @return The ODMStudy parsed from the ODM document.
     */
    public static ODMStudy parseStudyFromDocument(Document document, UMLSCodeDao umlsCodeDao) {
        // Get root element with tag name "Study"
        NodeList studies = document.getElementsByTagName("Study");
        Element studyNode = (Element) studies.item(0);
        // Get structural information for the Study from tag GlobalVariables
        Element globalVariables = (Element) studyNode.getElementsByTagName("GlobalVariables").item(0);
        String studyName = globalVariables.getElementsByTagName("StudyName").item(0).getTextContent();
        String studyDescription = globalVariables.getElementsByTagName("StudyDescription").item(0).getTextContent();
        String protocolName = globalVariables.getElementsByTagName("ProtocolName").item(0).getTextContent();
        ODMStudy study = new ODMStudy(studyNode.getAttribute("OID"), studyName, studyDescription, protocolName);
        // Get study content
        NodeList metaDataVersions = studyNode.getElementsByTagName("MetaDataVersion");
        List<ODMMetaDataVersion> odmMetaDataVersions = ODMMetaDataVersion.parseMetaDataVersions(metaDataVersions, study, umlsCodeDao);
        study.setMetaDataVersions(odmMetaDataVersions);
        // Return a new generated Study with parsed parameters above
        return study;
    }
}