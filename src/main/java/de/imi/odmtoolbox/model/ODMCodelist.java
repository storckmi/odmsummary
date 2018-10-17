package de.imi.odmtoolbox.model;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a Codelist from the ODM standard.
 *
 */
public class ODMCodelist {

    private String oid;
    private String name;
    private ODMItemDataType dataType;
    private List<ODMCodelistItem> codelistItems;

    public ODMCodelist() {
    }
    
    public ODMCodelist(String oid, String name, ODMItemDataType dataType) {
        this.oid = oid;
        this.name = name;
        this.dataType = dataType;
        this.codelistItems = new ArrayList<>();
    }
    
    public ODMCodelist(String oid, String name, ODMItemDataType dataType, List<ODMCodelistItem> codelistItems) {
        this.oid = oid;
        this.name = name;
        this.dataType = dataType;
        this.codelistItems = codelistItems;
    }

    public String getOID() {
        return oid;
    }

    public String getName() {
        return name;
    }

    public ODMItemDataType getDataType() {
        return dataType;
    }

    public List<ODMCodelistItem> getCodelistItems() {
        return codelistItems;
    }

    public Integer getCodeListItemCount() {
        return this.getCodelistItems().size();
    }

    public boolean isCoded() {
        for (ODMCodelistItem codelistItem : this.getCodelistItems()) {
            if (codelistItem.getUmlsCodeList() == null || codelistItem.getUmlsCodeList().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses a list of {@link ODMCodelist ODMCodelists} from a given XML
     * nodelist.
     *
     * @param codelists XML nodelist of codelists from the ODM standard.
     * @param umlsCodeDao The data access object for UMLS Codes, which are saved
     * to a database for further use.
     *
     * @return The list of parsed {@link ODMCodelist ODMCodelists}.
     */
    public static List<ODMCodelist> parseCodelists(NodeList codelists, UMLSCodeDao umlsCodeDao) {
        List<ODMCodelist> odmCodelists = new ArrayList<>();
        for (int i = 0; i < codelists.getLength(); i++) {
            Element codelist = (Element) codelists.item(i);
            String codelistOID = codelist.getAttribute("OID");
            String codelistName = codelist.getAttribute("Name");
            ODMItemDataType codelistDataType = ODMItemDataType.fromString(codelist.getAttribute("DataType"));
            NodeList codelistItems = codelist.getElementsByTagName("CodeListItem");
            NodeList enumeratedItems = codelist.getElementsByTagName("EnumeratedItem");
            
            ODMCodelist odmCodelist = new ODMCodelist(codelistOID, codelistName, codelistDataType);
            // If the codelist contains codelist items parse and attach them
            if (codelistItems.getLength() != 0) {
                odmCodelist = new ODMCodelist(codelistOID, codelistName, codelistDataType, ODMCodelistItem.parseCodelistItems(codelistItems, umlsCodeDao));
            } else if (enumeratedItems.getLength() != 0) {
                odmCodelist = new ODMCodelist(codelistOID, codelistName, codelistDataType, ODMCodelistItem.parseCodelistItems(enumeratedItems, umlsCodeDao));
            }
            odmCodelists.add(odmCodelist);
        }

        return odmCodelists;
    }

    /**
     * Compares the current {@link ODMCodelist} with a given {@link ODMCodelist}
     * and returns a {link ODMCompareType} for the relationship between these
     * two {@link ODMCodelist ODMCodelists}.
     *
     * @param compareCodelist The {@link ODMCodelist} which should be compared.
     * @return The {link ODMCompareType} which describes the relationship
     * between the two compared {@link ODMCodelist ODMCodelists}.
     */
    public ODMCompareType compareODMCodelist(ODMCodelist compareCodelist) {
        List<ODMCodelistItem> tempCodelistItems = this.getCodelistItems();
        List<ODMCodelistItem> compareCodelistItems = null;
        try {
            compareCodelistItems = compareCodelist.getCodelistItems();
        } catch (NullPointerException e) {
            return ODMCompareType.DIFFERENT;
        }
        // The compare type of all codelist items
        ODMCompareType codelistItemsCompareResult = null;

        // If count of codelistitems is different, the codelists are ODMCompareType.DIFFERENT
        if (tempCodelistItems.size() != compareCodelistItems.size()) {
            return ODMCompareType.DIFFERENT;
        } // Else the codelist items have to be proved, if they are identical or matching
        else {
            for (ODMCodelistItem codelistItem : tempCodelistItems) {
                Boolean codedValueCheck = null;
                for (ODMCodelistItem compareCodelistItem : compareCodelistItems) {
                    // If the current compared codelist item has no UMLS codes the codelists are ODMCompareType.NOTCODED 
                    if (compareCodelistItem.getUmlsCodeList().isEmpty()) {
                        return ODMCompareType.NOTCODED;
                    }
                    // If the UMLS codes and the coded value of the codelist items is identical the codelist item is identical
                    if (codelistItem.getUmlsCodeList().containsAll(compareCodelistItem.getUmlsCodeList()) && compareCodelistItem.getUmlsCodeList().containsAll(codelistItem.getUmlsCodeList())) {
                        if (codelistItem.getCodedValue().equalsIgnoreCase(compareCodelistItem.getCodedValue())) {
                            codedValueCheck = true;
                        }// If only the UMLS codes of the codelist items are identical the codelist item is matching
                        else {
                            codedValueCheck = false;
                        }
                    }
                }
                // If the current codelist item has no match in one of the codelist items of the compared codelist, the codelists are ODMCompareType.DIFFERENT
                if (codedValueCheck == null) {
                    return ODMCompareType.SIMILAR;
                } else if (codedValueCheck == true) {
                    // If the current codelist item is identical to one of the codelist items of the compared codelist and the compare type is not already ODMCompareType.TRANSFORMABLE, the compare type of all codelist items is set to ODMCompareType.IDENTICAL
                    if (codelistItemsCompareResult != ODMCompareType.TRANSFORMABLE) {
                        codelistItemsCompareResult = ODMCompareType.IDENTICAL;
                    }
                } else if (codedValueCheck == false) {
                    // If the current codelist item is transformable to one of the codelist items of the compared codelist the compare type is ODMCompareType.TRANSFORMABLE 
                    codelistItemsCompareResult = ODMCompareType.TRANSFORMABLE;
                }
            }
        }
        if (codelistItemsCompareResult.equals(ODMCompareType.IDENTICAL)) {
            if (this.getDataType().equals(compareCodelist.getDataType())) {
                // If all codelist items are identical and the datatype and the name of the codelists is identical the codelists are ODMCompareType.IDENTICAL
                if (this.getName().equalsIgnoreCase(compareCodelist.getName())) {
                    return ODMCompareType.IDENTICAL;
                }// If only the name of the codelists differs the codelists are ODMCompareType.MATCHING
                else {
                    return ODMCompareType.MATCHING;
                }
            } // If only the codelist items are transformable the codelists are ODMCompareType.TRANSFORMABLE
            else {
                return ODMCompareType.TRANSFORMABLE;
            }
        } else {
            return ODMCompareType.TRANSFORMABLE;
        }
    }

    @Override
    public int hashCode() {
        return getOID().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ODMCodelist)) {
            return false;
        }
        final ODMCodelist other = (ODMCodelist) obj;
        return getOID().equals(other.getOID());
    }
}
