package de.imi.odmtoolbox.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class represents a FormDef from the ODM standard.
 *
 */
public class ODMForm {

    private String oid;
    private String name;
    List<ODMFormODMItemGroup> itemGroups = new ArrayList<>();
    private ODMStudy study;

    public ODMForm(String oid, String name, ODMStudy study) {
        this.oid = oid;
        this.name = name;
        this.study = study;
    }

    public String getOID() {
        return oid;
    }

    public String getName() {
        return name;
    }

    public List<ODMFormODMItemGroup> getItemGroups() {
        return itemGroups;
    }

    public ODMStudy getStudy() {
        return study;
    }

    public Set<ODMItem> getItems() {
        Set<ODMItem> items = new HashSet<>();
        for (ODMFormODMItemGroup itemGroup : getItemGroups()) {
            for (ODMItemGroupODMItem item : itemGroup.getItemGroup().getItems()) {
                items.add(item.getItem());
            }
        }
        return items;
    }

    public List<ODMItem> getAllItems() {
        List<ODMItem> items = new ArrayList<>();
        for (ODMFormODMItemGroup itemGroup : getItemGroups()) {
            for (ODMItemGroupODMItem item : itemGroup.getItemGroup().getItems()) {
                items.add(item.getItem());
            }
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
        for (ODMFormODMItemGroup itemGroup : getItemGroups()) {
            codelists.addAll(itemGroup.getItemGroup().getCodelists());
        }
        return codelists;
    }

    public Integer getCodeListCount() {
        return getCodelists().size();
    }

    public Integer getItemGroupCount() {
        return getItemGroups().size();
    }

    public void addItemGroup(ODMFormODMItemGroup itemGroup) {
        this.itemGroups.add(itemGroup);
    }

    /**
     * Parses a list of {@link ODMForm ODMForms} from a given XML nodelist.
     *
     * @param forms XML nodelist forms from the ODM standard.
     * @param itemGroups Beforehand parsed {@link ODMItemGroup ODMItemGroups}
     * from the same XML document.
     * @param study Beforehand parsed {@link ODMStudy} from the same XML
     * document.
     *
     * @return The list of parsed {@link ODMForm ODMForms}.
     */
    public static List<ODMForm> parseForms(NodeList forms, List<ODMItemGroup> itemGroups, ODMStudy study) {
        List<ODMForm> odmForms = new ArrayList<>();
        for (int i = 0; i < forms.getLength(); i++) {
            Element form = (Element) forms.item(i);
            String formOID = form.getAttribute("OID");
            String formName = form.getAttribute("Name");
            ODMForm odmForm = new ODMForm(formOID, formName, study);
            NodeList itemGroupRef = form.getElementsByTagName("ItemGroupRef");
            String itemGroupOID = null;
            // Loop through attached item group references
            for (int j = 0; j < itemGroupRef.getLength(); j++) {
                Element itemGroupRefElement = (Element) itemGroupRef.item(j);
                if (itemGroupRef != null && itemGroupRefElement.hasAttribute("ItemGroupOID")) {
                    itemGroupOID = itemGroupRefElement.getAttribute("ItemGroupOID");
                }
                // If the item group belongs to the current form then attach it
                if (itemGroupOID != null) {
                    for (ODMItemGroup itemGroup : itemGroups) {
                        if (itemGroup.getOID().equals(itemGroupOID)) {
                            ODMFormODMItemGroup formItemGroup = new ODMFormODMItemGroup(odmForm, itemGroup);
                            if (itemGroupRefElement.hasAttribute("Mandatory")) {
                                if (itemGroupRefElement.getAttribute("Mandatory").equalsIgnoreCase("yes")) {
                                    formItemGroup.setMandatory(Boolean.TRUE);
                                } else {
                                    formItemGroup.setMandatory(Boolean.FALSE);
                                }
                            }
                            if (itemGroupRefElement.hasAttribute("OrderNumber")) {
                                formItemGroup.setOrderNumber(Integer.parseInt(itemGroupRefElement.getAttribute("OrderNumber")));
                            }
                            odmForm.addItemGroup(formItemGroup);
                            break;
                        }
                    }
                }
            }
            odmForms.add(odmForm);
        }
        return odmForms;
    }
}
