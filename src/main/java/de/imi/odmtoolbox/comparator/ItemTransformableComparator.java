package de.imi.odmtoolbox.comparator;

import de.imi.odmtoolbox.model.ODMCompareType;
import de.imi.odmtoolbox.model.ODMComparedItem;
import de.imi.odmtoolbox.model.ODMItem;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts {@link ODMComparedItem ODMComparedItems} by transformable item count.
 *
 */
public class ItemTransformableComparator implements Comparator<ODMComparedItem> {

    @Override
    public int compare(ODMComparedItem item, ODMComparedItem compareItem) {
        if (item.getItem().compareODMItem(compareItem.getItem()).equals(ODMCompareType.IDENTICAL)) {
            return 0;
        } else {
            Integer itemCount = 0;
            Integer compareItemCount = 0;
            for (List<ODMItem> items : item.getTransformableFormItems().values()) {
                itemCount += items.size();
            }
            for (List<ODMItem> items : compareItem.getTransformableFormItems().values()) {
                compareItemCount += items.size();
            }
            if (itemCount - compareItemCount != 0) {
                return compareItemCount - itemCount;
            } else if ((item.getItem().getName().compareTo(compareItem.getItem().getName())) != 0) {
                return item.getItem().getName().compareTo(compareItem.getItem().getName());
            } else {
                return 1;
            }
        }
    }
}
