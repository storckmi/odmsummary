package de.imi.odmtoolbox.library;

import de.imi.odmtoolbox.comparator.FormPerStudyComparator;
import de.imi.odmtoolbox.comparator.ItemComparableComparator;
import de.imi.odmtoolbox.comparator.ItemIdenticalComparator;
import de.imi.odmtoolbox.comparator.ItemMatchingComparator;
import de.imi.odmtoolbox.comparator.ItemNotCodedComparator;
import de.imi.odmtoolbox.comparator.ItemSimilarComparator;
import de.imi.odmtoolbox.comparator.ItemTransformableComparator;
import de.imi.odmtoolbox.model.ODMCompareType;
import de.imi.odmtoolbox.model.ODMComparedItem;
import de.imi.odmtoolbox.model.ODMForm;
import de.imi.odmtoolbox.model.ODMItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class compares all {@link ODMItem ODMItems} in all given
 * {@link ODMForm ODMForms} pairwise.
 *
 */
public class ODMCompare {

    /**
     * Compares all {@link ODMItem ODMItems} in all given
     * {@link ODMForm ODMForms} pairwise.
     *
     * @param forms The {@link ODMForm ODMForms} which should be compared.
     * @return The comparison results for the given {@link ODMForm ODMForms}
     * grouped by {@link ODMCompareType}.
     */
    public static Map<ODMCompareType, TreeSet<ODMComparedItem>> compareODMItems(TreeSet<ODMForm> forms) {
        Iterator<ODMForm> formIterator = forms.iterator();
        Map<ODMCompareType, TreeSet<ODMComparedItem>> comparedItems = new HashMap<>();
        // Loop through all forms
        while (formIterator.hasNext()) {
            ODMForm form = formIterator.next();
            ItemIdenticalComparator itemIdenticalComparator = new ItemIdenticalComparator();
            TreeSet<ODMComparedItem> identicalComparedItems = new TreeSet<>(itemIdenticalComparator);
            ItemMatchingComparator itemMatchingComparator = new ItemMatchingComparator();
            TreeSet<ODMComparedItem> matchingComparedItems = new TreeSet<>(itemMatchingComparator);
            ItemTransformableComparator itemTransformableComparator = new ItemTransformableComparator();
            TreeSet<ODMComparedItem> transformableComparedItems = new TreeSet<>(itemTransformableComparator);
            ItemSimilarComparator itemSimilarComparator = new ItemSimilarComparator();
            TreeSet<ODMComparedItem> similarComparedItems = new TreeSet<>(itemSimilarComparator);
            ItemComparableComparator itemComparableComparator = new ItemComparableComparator();
            TreeSet<ODMComparedItem> comparableComparedItems = new TreeSet<>(itemComparableComparator);
            ItemNotCodedComparator itemNotCodedComparator = new ItemNotCodedComparator();
            TreeSet<ODMComparedItem> notCodedComparedItems = new TreeSet<>(itemNotCodedComparator);
            ODMComparedItem comparedItem = null;
            // Loop through the items in the current form
            for (ODMItem item : form.getAllItems()) {
                comparedItem = new ODMComparedItem(item, form);
                if (item.getUmlsCodeList() == null || item.getUmlsCodeList().isEmpty()) {
                    notCodedComparedItems.add(comparedItem);
                    continue;
                }
                Integer identicalItemCount = 0;
                Integer matchingItemCount = 0;
                Integer transformableItemCount = 0;
                Integer similarItemCount = 0;
                Integer comparableItemCount = 0;
                Comparator<ODMForm> formComparator = new FormPerStudyComparator();
                TreeMap<ODMForm, List<ODMItem>> identicalFormItems = new TreeMap<>(formComparator);
                TreeMap<ODMForm, List<ODMItem>> matchingFormItems = new TreeMap<>(formComparator);
                TreeMap<ODMForm, List<ODMItem>> transformableFormItems = new TreeMap<>(formComparator);
                TreeMap<ODMForm, List<ODMItem>> similarFormItems = new TreeMap<>(formComparator);
                TreeMap<ODMForm, List<ODMItem>> comparableFormItems = new TreeMap<>(formComparator);
                Iterator<ODMForm> compareFormIterator = forms.iterator();
                // Loop through all forms to compare the current item to all other items
                while (compareFormIterator.hasNext()) {
                    ODMForm compareForm = compareFormIterator.next();
                    List<ODMItem> identicalItems = new ArrayList<>();
                    List<ODMItem> matchingItems = new ArrayList<>();
                    List<ODMItem> transformableItems = new ArrayList<>();
                    List<ODMItem> similarItems = new ArrayList<>();
                    List<ODMItem> comparableItems = new ArrayList<>();
                    for (ODMItem compareItem : compareForm.getAllItems()) {
                        // Compare two items and put the result in the corresponding data structure
                        ODMCompareType compareType = item.compareODMItem(compareItem);
                        switch (compareType) {
                            case IDENTICAL:
                                identicalItems.add(compareItem);
                                comparableItems.add(compareItem);
                                comparableItemCount += 1;
                                identicalItemCount += 1;
                                break;
                            case MATCHING:
                                matchingItems.add(compareItem);
                                comparableItems.add(compareItem);
                                comparableItemCount += 1;
                                matchingItemCount += 1;
                                break;
                            case TRANSFORMABLE:
                                transformableItems.add(compareItem);
                                comparableItems.add(compareItem);
                                comparableItemCount += 1;
                                transformableItemCount += 1;
                                break;
                            case SIMILAR:
                                similarItems.add(compareItem);
                                comparableItems.add(compareItem);
                                comparableItemCount += 1;
                                similarItemCount += 1;
                                break;
                            case DIFFERENT:
                                break;
                        }
                    }
                    identicalFormItems.put(compareForm, identicalItems);
                    matchingFormItems.put(compareForm, matchingItems);
                    transformableFormItems.put(compareForm, transformableItems);
                    similarFormItems.put(compareForm, similarItems);
                    comparableFormItems.put(compareForm, comparableItems);
                }
                // Put the comparison results for the item in the corresponding result for the form
                if (identicalItemCount > 1) {
                    comparedItem.getIdenticalFormItems().putAll(identicalFormItems);
                    identicalComparedItems.add(comparedItem);
                }
                if (matchingItemCount > 0) {
                    comparedItem.getMatchingFormItems().putAll(matchingFormItems);
                    matchingComparedItems.add(comparedItem);
                }
                if (transformableItemCount > 0) {
                    comparedItem.getTransformableFormItems().putAll(transformableFormItems);
                    transformableComparedItems.add(comparedItem);
                }
                if (similarItemCount > 0) {
                    comparedItem.getSimilarFormItems().putAll(similarFormItems);
                    similarComparedItems.add(comparedItem);
                }
                if (comparableItemCount > 1) {
                    comparedItem.getComparableFormItems().putAll(comparableFormItems);
                    comparableComparedItems.add(comparedItem);
                }

            }
            // Put the comparison results for the item in the compared items data structure
            if (!identicalComparedItems.isEmpty()) {
                if (comparedItems.containsKey(ODMCompareType.IDENTICAL)) {
                    comparedItems.get(ODMCompareType.IDENTICAL).addAll(identicalComparedItems);
                } else {
                    comparedItems.put(ODMCompareType.IDENTICAL, identicalComparedItems);
                }
            }
            if (!matchingComparedItems.isEmpty()) {
                if (comparedItems.containsKey(ODMCompareType.MATCHING)) {
                    comparedItems.get(ODMCompareType.MATCHING).addAll(matchingComparedItems);
                } else {
                    comparedItems.put(ODMCompareType.MATCHING, matchingComparedItems);
                }
            }
            if (!transformableComparedItems.isEmpty()) {
                if (comparedItems.containsKey(ODMCompareType.TRANSFORMABLE)) {
                    comparedItems.get(ODMCompareType.TRANSFORMABLE).addAll(transformableComparedItems);
                } else {
                    comparedItems.put(ODMCompareType.TRANSFORMABLE, transformableComparedItems);
                }
            }
            if (!similarComparedItems.isEmpty()) {
                if (comparedItems.containsKey(ODMCompareType.SIMILAR)) {
                    comparedItems.get(ODMCompareType.SIMILAR).addAll(similarComparedItems);
                } else {
                    comparedItems.put(ODMCompareType.SIMILAR, similarComparedItems);
                }
            }
            if (!comparableComparedItems.isEmpty()) {
                if (comparedItems.containsKey(ODMCompareType.COMPARABLE)) {
                    comparedItems.get(ODMCompareType.COMPARABLE).addAll(comparableComparedItems);
                } else {
                    comparedItems.put(ODMCompareType.COMPARABLE, comparableComparedItems);
                }
            }
            if (!notCodedComparedItems.isEmpty()) {
                if (comparedItems.containsKey(ODMCompareType.NOTCODED)) {
                    comparedItems.get(ODMCompareType.NOTCODED).addAll(notCodedComparedItems);
                } else {
                    comparedItems.put(ODMCompareType.NOTCODED, notCodedComparedItems);
                }
            }
        }
        return comparedItems;
    }
}
