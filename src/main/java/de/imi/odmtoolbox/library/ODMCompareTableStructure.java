package de.imi.odmtoolbox.library;

import de.imi.odmtoolbox.model.ODMCompareType;
import de.imi.odmtoolbox.model.ODMComparedItem;
import de.imi.odmtoolbox.model.ODMForm;
import de.imi.odmtoolbox.model.ODMItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class represents the data structure including all information for the
 * comparison.
 *
 */
public class ODMCompareTableStructure {

    HashMap<String, Integer> identicalFormWidth;
    HashMap<String, Integer> matchingFormWidth;
    HashMap<String, Integer> transformableFormWidth;
    HashMap<String, Integer> similarFormWidth;
    HashMap<String, Integer> comparableFormWidth;

    public ODMCompareTableStructure(Map<ODMCompareType, TreeSet<ODMComparedItem>> comparedItems) {
        if (comparedItems.get(ODMCompareType.IDENTICAL) != null && !comparedItems.get(ODMCompareType.IDENTICAL).isEmpty()) {
            identicalFormWidth = getFormWidths(comparedItems.get(ODMCompareType.IDENTICAL), ODMCompareType.IDENTICAL);
        }
        if (comparedItems.get(ODMCompareType.MATCHING) != null && !comparedItems.get(ODMCompareType.MATCHING).isEmpty()) {
            matchingFormWidth = getFormWidths(comparedItems.get(ODMCompareType.MATCHING), ODMCompareType.MATCHING);
        }
        if (comparedItems.get(ODMCompareType.TRANSFORMABLE) != null && !comparedItems.get(ODMCompareType.TRANSFORMABLE).isEmpty()) {
            transformableFormWidth = getFormWidths(comparedItems.get(ODMCompareType.TRANSFORMABLE), ODMCompareType.TRANSFORMABLE);
        }
        if (comparedItems.get(ODMCompareType.SIMILAR) != null && !comparedItems.get(ODMCompareType.SIMILAR).isEmpty()) {
            similarFormWidth = getFormWidths(comparedItems.get(ODMCompareType.SIMILAR), ODMCompareType.SIMILAR);
        }
        if (comparedItems.get(ODMCompareType.COMPARABLE) != null && !comparedItems.get(ODMCompareType.COMPARABLE).isEmpty()) {
            comparableFormWidth = getFormWidths(comparedItems.get(ODMCompareType.COMPARABLE), ODMCompareType.COMPARABLE);
        }
    }

    /**
     * Returns the width of the table which presents the comparison data for a
     * given {@link ODMCompareType} to the user.
     *
     * @param comparedItems The set of all
     * {@link ODMComparedItem ODMComparedItems}.
     * @param compareType The {@link ODMCompareType} for which the form width
     * should be returned.
     * @return The width of the table which presents the comparison data for a
     * given {@link ODMCompareType}.
     */
    private HashMap<String, Integer> getFormWidths(TreeSet<ODMComparedItem> comparedItems, ODMCompareType compareType) {
        HashMap<ODMForm, HashSet<Integer>> formItemCount = new HashMap<ODMForm, HashSet<Integer>>();
        TreeMap<ODMForm, List<ODMItem>> formItems = new TreeMap<>();
        for (ODMComparedItem comparedItem : comparedItems) {
            switch (compareType) {
                case IDENTICAL:
                    formItems = comparedItem.getIdenticalFormItems();
                    break;
                case MATCHING:
                    formItems = comparedItem.getMatchingFormItems();
                    break;
                case TRANSFORMABLE:
                    formItems = comparedItem.getTransformableFormItems();
                    break;
                case SIMILAR:
                    formItems = comparedItem.getSimilarFormItems();
                    break;
                case COMPARABLE:
                    formItems = comparedItem.getComparableFormItems();
                    break;
            }

            for (Map.Entry<ODMForm, List<ODMItem>> entry : formItems.entrySet()) {
                if (!formItemCount.containsKey(entry.getKey())) {
                    HashSet<Integer> itemCount = new HashSet<Integer>();
                    formItemCount.put(entry.getKey(), itemCount);
                }
                if (!entry.getValue().isEmpty()) {
                    formItemCount.get(entry.getKey()).add(entry.getValue().size());
                } else {
                    formItemCount.get(entry.getKey()).add(1);
                }
            }
        }
        for (HashSet<Integer> itemCount : formItemCount.values()) {
            Iterator<Integer> formWidthIterator = itemCount.iterator();
            while (formWidthIterator.hasNext()) {
                Integer currentItemCount = formWidthIterator.next();
                Iterator<Integer> formWidthCompareIterator = itemCount.iterator();
                while (formWidthCompareIterator.hasNext()) {
                    Integer compareItemCount = formWidthCompareIterator.next();
                    if (currentItemCount != compareItemCount && compareItemCount % currentItemCount == 0) {
                        formWidthIterator.remove();
                        break;
                    }
                }
            }
        }
        HashMap<String, Integer> formWidths = new HashMap<>();
        for (Map.Entry<ODMForm, HashSet<Integer>> entry : formItemCount.entrySet()) {
            Integer formWidth = 1;
            for (Integer itemCount : entry.getValue()) {
                formWidth *= itemCount;
            }
            formWidths.put(entry.getKey().toString(), formWidth);
        }
        return formWidths;
    }

    public HashMap<String, Integer> getIdenticalFormWidth() {
        return identicalFormWidth;
    }

    public HashMap<String, Integer> getMatchingFormWidth() {
        return matchingFormWidth;
    }

    public HashMap<String, Integer> getTransformableFormWidth() {
        return transformableFormWidth;
    }

    public HashMap<String, Integer> getSimilarFormWidth() {
        return similarFormWidth;
    }

    public HashMap<String, Integer> getComparableFormWidth() {
        return comparableFormWidth;
    }
}
