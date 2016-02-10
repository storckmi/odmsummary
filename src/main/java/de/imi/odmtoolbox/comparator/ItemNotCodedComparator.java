package de.imi.odmtoolbox.comparator;

import de.imi.odmtoolbox.model.ODMComparedItem;
import java.util.Comparator;

/**
 * Sorts not coded {@link ODMComparedItem ODMComparedItems} by name.
 *
 */
public class ItemNotCodedComparator implements Comparator<ODMComparedItem> {

    @Override
    public int compare(ODMComparedItem item, ODMComparedItem compareItem) {
        if (item.getForm().getStudy().getName().compareToIgnoreCase(compareItem.getForm().getStudy().getName()) != 0) {
            return item.getForm().getStudy().getName().compareToIgnoreCase(compareItem.getForm().getStudy().getName());
        } else if (item.getForm().getOID().compareToIgnoreCase(compareItem.getForm().getOID()) != 0) {
            return item.getForm().getOID().compareToIgnoreCase(compareItem.getForm().getOID());
        } else if (item.getItem().getOID().compareToIgnoreCase(compareItem.getItem().getOID()) != 0) {
            return item.getItem().getOID().compareToIgnoreCase(compareItem.getItem().getOID());
        } else {
            return 1;
        }
    }
}
