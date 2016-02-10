package de.imi.odmtoolbox.model;

import de.imi.odmtoolbox.comparator.FormPerStudyComparator;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * This class holds the information of all compared data items.
 *
 */
public class ODMComparedItem {

    private ODMItem item;
    private ODMForm form;
    Comparator<ODMForm> formComparator = new FormPerStudyComparator();
    private TreeMap<ODMForm, List<ODMItem>> identicalFormItems;
    private TreeMap<ODMForm, List<ODMItem>> matchingFormItems;
    private TreeMap<ODMForm, List<ODMItem>> transformableFormItems;
    private TreeMap<ODMForm, List<ODMItem>> similarFormItems;
    private TreeMap<ODMForm, List<ODMItem>> comparableFormItems;

    public ODMComparedItem(ODMItem item, ODMForm form) {
        this.item = item;
        this.form = form;
        identicalFormItems = new TreeMap<>(formComparator);
        matchingFormItems = new TreeMap<>(formComparator);
        transformableFormItems = new TreeMap<>(formComparator);
        similarFormItems = new TreeMap<>(formComparator);
        comparableFormItems = new TreeMap<>(formComparator);
    }

    public ODMItem getItem() {
        return item;
    }

    public ODMForm getForm() {
        return form;
    }

    public TreeMap<ODMForm, List<ODMItem>> getIdenticalFormItems() {
        return identicalFormItems;
    }

    public TreeMap<ODMForm, List<ODMItem>> getMatchingFormItems() {
        return matchingFormItems;
    }

    public TreeMap<ODMForm, List<ODMItem>> getTransformableFormItems() {
        return transformableFormItems;
    }

    public TreeMap<ODMForm, List<ODMItem>> getSimilarFormItems() {
        return similarFormItems;
    }

    public TreeMap<ODMForm, List<ODMItem>> getComparableFormItems() {
        return comparableFormItems;
    }
}
