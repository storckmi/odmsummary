package de.imi.odmtoolbox.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a ItemGroupDef from the ODM standard.
 * 
 */
public class ODMItemGroup {

    private String oid;
    private String name;
    private List<ODMItemGroupODMItem> items = new ArrayList<>();

    private ODMItemGroup(String oid, String name) {
        this.oid = oid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getOID() {
        return oid;
    }

    public List<ODMItemGroupODMItem> getItems() {
        return items;
    }

    public Integer getItemCount() {
        return this.getItems().size();
    }

    public void addItem(ODMItemGroupODMItem item) {
        this.items.add(item);
    }

    public Integer getCodeListItemCount() {
        Integer codelistItemCount = 0;
        for (ODMCodelist codelist : getCodelists()) {
            codelistItemCount += codelist.getCodeListItemCount();
        }
        return codelistItemCount;
    }

    public Set<ODMCodelist> getCodelists() {
        Set<ODMCodelist> codelists = new HashSet<ODMCodelist>();
        for (ODMItemGroupODMItem item : getItems()) {
            if (item.getItem().getCodelist() != null) {
                codelists.add(item.getItem().getCodelist());
            }
        }
        return codelists;
    }

    public Integer getCodeListCount() {
        return getCodelists().size();
    }

    /**
     * Parses a list of {@link ODMItemGroup ODMItemGroups} from a given XML nodelist.
     * 
     * @param itemGroups XML nodelist of itemgroups from the ODM standard.
     * @param items Beforehand parsed {@link ODMItem ODMItems} from the same XML document.
     * 
     * @return The list of parsed {@link ODMItemGroup ODMItemGroups}.
     */
    public static List<ODMItemGroup> parseItemGroups(NodeList itemGroups, List<ODMItem> items) {
        List<ODMItemGroup> odmItemGroups = new ArrayList<>();
        for (int i = 0; i < itemGroups.getLength(); i++) {
            Element itemGroup = (Element) itemGroups.item(i);
            String itemGroupOID = itemGroup.getAttribute("OID");
            String itemGroupName = itemGroup.getAttribute("Name");
            ODMItemGroup odmItemGroup = new ODMItemGroup(itemGroupOID, itemGroupName);
            NodeList itemRef = itemGroup.getElementsByTagName("ItemRef");
            String itemOID = null;
            // Loop through attached item references
            for (int j = 0; j < itemRef.getLength(); j++) {
                Element itemRefElement = (Element) itemRef.item(j);
                if (itemRef != null && itemRefElement.hasAttribute("ItemOID")) {
                    itemOID = itemRefElement.getAttribute("ItemOID");
                }
                // If the item belongs to the current item group then attach it
                if (itemOID != null) {
                    for (ODMItem item : items) {
                        if (item.getOID().equals(itemOID)) {
                            ODMItemGroupODMItem itemGroupItem = new ODMItemGroupODMItem(odmItemGroup, item);
                            if (itemRefElement.hasAttribute("Mandatory")) {
                                if (itemRefElement.getAttribute("Mandatory").equalsIgnoreCase("yes")) {
                                    itemGroupItem.setMandatory(Boolean.TRUE);
                                } else {
                                    itemGroupItem.setMandatory(Boolean.FALSE);
                                }
                            }
                            if (itemRefElement.hasAttribute("OrderNumber")) {
                                itemGroupItem.setOrderNumber(Integer.parseInt(itemRefElement.getAttribute("OrderNumber")));
                            }
                            odmItemGroup.addItem(itemGroupItem);
                            break;
                        }
                    }
                }
            }
            odmItemGroups.add(odmItemGroup);
        }
        return odmItemGroups;
    }
}
