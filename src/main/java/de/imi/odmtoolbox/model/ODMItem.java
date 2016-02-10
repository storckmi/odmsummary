package de.imi.odmtoolbox.model;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a ItemDef from the ODM standard.
 *
 */
public class ODMItem {

    private Long id;
    private String oid;
    private String name;
    private ODMItemDataType dataType;
    private ODMCodelist codelist;
    private Set<UMLSCode> umlsCodeList = new HashSet<>();

    public ODMItem() {
    }

    public ODMItem(String oid, String name, ODMItemDataType dataType) {
        this.oid = oid;
        this.name = name;
        this.dataType = dataType;
    }

    public void setCodelist(ODMCodelist codelist) {
        this.codelist = codelist;
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

    public ODMCodelist getCodelist() {
        return codelist;
    }

    public Integer getCodeListItemCount() {
        if (getCodelist() != null) {
            return this.getCodelist().getCodeListItemCount();
        } else {
            return 0;
        }
    }

    public Integer getCodeListCount() {
        if (getCodelist() != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public Set<UMLSCode> getUmlsCodeList() {
        return umlsCodeList;
    }

    public void addUMLSCode(UMLSCode umlsCode) {
        this.umlsCodeList.add(umlsCode);
    }

    public void addAllUMLSCode(Collection<UMLSCode> umlsCode) {
        this.umlsCodeList.addAll(umlsCode);
    }
    
    /**
     * Parses a list of {@link ODMItem ODMItems} from a given XML nodelist.
     * 
     * @param items XML nodelist of items from the ODM standard.
     * @param codelists Beforehand parsed {@link ODMCodelist ODMCodelists} from the same XML document.
     * @param umlsCodeDao The data access object for UMLS Codes, which are
     * saved to a database for further use.
     * 
     * @return The list of parsed {@link ODMItem ODMItems}.
     */
    public static List<ODMItem> parseItems(NodeList items, List<ODMCodelist> codelists, UMLSCodeDao umlsCodeDao) {
        List<ODMItem> odmItems = new ArrayList<>();
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            String itemOID = item.getAttribute("OID");
            String itemName = item.getAttribute("Name");
            ODMItemDataType itemDataType = ODMItemDataType.fromString(item.getAttribute("DataType"));
            ODMItem odmItem = new ODMItem(itemOID, itemName, itemDataType);
            Element codelistRef = (Element) item.getElementsByTagName("CodeListRef").item(0);
            String codelistOID = null;
            // If there is a reference to a codelist attach the right one
            if (codelistRef != null && codelistRef.hasAttribute("CodeListOID")) {
                codelistOID = codelistRef.getAttribute("CodeListOID");
            }
            if (codelistOID != null) {
                for (ODMCodelist codelist : codelists) {
                    if (codelist.getOID().equals(codelistOID)) {
                        odmItem.setCodelist(codelist);
                        break;
                    }
                }
            }
            NodeList umlsCodes = item.getElementsByTagName("Alias");
            // Loop through the Aliases and attach the UMLS codes
            for (int j = 0; j < umlsCodes.getLength(); j++) {
                Element code = (Element) umlsCodes.item(j);
                if (code.hasAttribute("Context") && code.getAttribute("Context").contains("UMLS") && code.hasAttribute("Name")) {
                    UMLSCode umlsCode = umlsCodeDao.getUMLSCodeByCode(code.getAttribute("Name"));
                    if (umlsCode == null) {
                        umlsCode = new UMLSCode(code.getAttribute("Name"));
                        umlsCodeDao.merge(umlsCode);
                    }
                    odmItem.addUMLSCode(umlsCode);
                }
            }
            odmItems.add(odmItem);
        }
        return odmItems;
    }
    
    /**
     * Compares the current {@link ODMItem} with a given {@link ODMItem}
     * and returns a {link ODMCompareType} for the relationship between these
     * two {@link ODMItem ODMItems}.
     *
     * @param compareItem The {@link ODMItem} which should be compared.
     * @return The {link ODMCompareType} which describes the relationship
     * between the two compared {@link ODMItem ODMItems}.
     */
    public ODMCompareType compareODMItem(ODMItem compareItem) {
        ODMCompareType codelistCompareResult = null;
        // If the item has no codelist the codelistCompareResult has to be null
        if (this.getCodeListItemCount() == 0) {
            codelistCompareResult = null;
        } // If the current codelist is coded with UMLS codes the codelists have to be compared
        else if (this.getCodeListItemCount() > 0 && this.getCodelist().isCoded()) {
            codelistCompareResult = this.getCodelist().compareODMCodelist(compareItem.getCodelist());
        } // If the current codelist is not coded with UMLS codes the codelists are ODMCompareType.NOTCODED 
        else {
            codelistCompareResult = ODMCompareType.NOTCODED;
        }
        Boolean nameCompare = this.getName().equalsIgnoreCase(compareItem.getName());
        Boolean dataTypeCompare = this.getDataType().equals(compareItem.getDataType());
        // Compare the attached code lists and return the appropriate compare type
        Boolean umlsCodeCompare = this.getUmlsCodeList().containsAll(compareItem.getUmlsCodeList()) && compareItem.getUmlsCodeList().containsAll(this.getUmlsCodeList());
        if (codelistCompareResult == null) {
            if (umlsCodeCompare == true) {
                if (dataTypeCompare == true) {
                    if (nameCompare == true) {
                        return ODMCompareType.IDENTICAL;
                    } else {
                        return ODMCompareType.MATCHING;
                    }
                } else {
                    return ODMCompareType.SIMILAR;
                }
            }
            return ODMCompareType.DIFFERENT;
        } else {
            switch (codelistCompareResult) {
                case IDENTICAL:
                    if (umlsCodeCompare == true) {
                        if (nameCompare == true) {
                            if (dataTypeCompare == true) {
                                return ODMCompareType.IDENTICAL;
                            } else {
                                return ODMCompareType.SIMILAR;
                            }
                        } else {
                            return ODMCompareType.MATCHING;
                        }
                    } else {
                        return ODMCompareType.DIFFERENT;
                    }
                case MATCHING:
                    if (umlsCodeCompare == true) {
                        if (dataTypeCompare == true) {
                            return ODMCompareType.MATCHING;
                        } else {
                            return ODMCompareType.TRANSFORMABLE;
                        }
                    } else {
                        return ODMCompareType.DIFFERENT;
                    }
                case TRANSFORMABLE:
                    if (umlsCodeCompare == true) {
                        return ODMCompareType.TRANSFORMABLE;
                    } else {
                        return ODMCompareType.DIFFERENT;
                    }
                case SIMILAR:
                    if (umlsCodeCompare == true) {
                        return ODMCompareType.SIMILAR;
                    } else {
                        return ODMCompareType.DIFFERENT;
                    }
                case DIFFERENT:
                    return ODMCompareType.DIFFERENT;
                case NOTCODED:
                    return ODMCompareType.DIFFERENT;
                default:
                    return ODMCompareType.DIFFERENT;
            }
        }
    }

    @Override
    public boolean equals(Object compareItem) {
        if (compareItem instanceof ODMItem) {
            return this.compareODMItem((ODMItem) compareItem).equals(ODMCompareType.IDENTICAL);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + (this.dataType != null ? this.dataType.hashCode() : 0);
        hash = 11 * hash + Objects.hashCode(this.umlsCodeList);
        return hash;
    }
}
