package de.imi.odmtoolbox.comparator;

import de.imi.odmtoolbox.model.ODMStudy;
import java.util.Comparator;

/**
 * Sorts {@link ODMStudy ODMStudies} by name.
 *
 */
public class StudyComparator implements Comparator<ODMStudy> {

    @Override
    public int compare(ODMStudy study, ODMStudy compareStudy) {
        return study.getName().compareTo(compareStudy.getName());
    }
}
