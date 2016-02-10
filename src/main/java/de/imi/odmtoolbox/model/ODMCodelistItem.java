package de.imi.odmtoolbox.model;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a CodelistItem from the ODM standard.
 *
 */
public class ODMCodelistItem {

    private String codedValue;
    private String name;
    private Set<UMLSCode> umlsCodeList = new HashSet<>();

    public ODMCodelistItem() {
    }

    public ODMCodelistItem(String codedValue, String name) {
        this.codedValue = codedValue;
        this.name = name;
    }

    public String getCodedValue() {
        return codedValue;
    }

    public String getName() {
        return name;
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
     * Parses a list of {@link ODMCodelistItem ODMCodelistItems} from a given
     * XML nodelist.
     *
     * @param codelistItems XML nodelist of codelist items from the ODM
     * standard.
     * @param umlsCodeDao The data access object for UMLS Codes, which are saved
     * to a database for further use.
     *
     * @return The list of parsed {@link ODMCodelistItem ODMCodelistItems}.
     */
    public static List<ODMCodelistItem> parseCodelistItems(NodeList codelistItems, UMLSCodeDao umlsCodeDao) {
        List<ODMCodelistItem> odmCodelistItems = new ArrayList<>();
        for (int i = 0; i < codelistItems.getLength(); i++) {
            Element codelistItem = (Element) codelistItems.item(i);
            ODMCodelistItem odmCodelistItem = new ODMCodelistItem(codelistItem.getAttribute("CodedValue"), codelistItem.getTextContent());
            NodeList umlsCodes = codelistItem.getElementsByTagName("Alias");
            for (int j = 0; j < umlsCodes.getLength(); j++) {
                Element code = (Element) umlsCodes.item(j);
                // Add the attached UMLS codes to the codelist items
                if (code.hasAttribute("Context") && code.getAttribute("Context").contains("UMLS") && code.hasAttribute("Name")) {
                    UMLSCode umlsCode = umlsCodeDao.getUMLSCodeByCode(code.getAttribute("Name"));
                    if (umlsCode == null) {
                        umlsCode = new UMLSCode(code.getAttribute("Name"));
                    }
                    odmCodelistItem.addUMLSCode(umlsCode);
                    umlsCodeDao.merge(umlsCode);
                }
            }
            odmCodelistItems.add(odmCodelistItem);
        }
        return odmCodelistItems;
    }
}
