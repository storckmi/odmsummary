package de.imi.odmtoolbox.comparator;

import de.imi.odmtoolbox.model.ODMForm;
import java.util.Comparator;

/**
 * Sorts {@link ODMForm ODMForms} by name of the corresponding
 * {@link de.imi.odmtoolbox.model.ODMStudy} or by its name if the study is
 * identical.
 *
 */
public class FormPerStudyComparator implements Comparator<ODMForm> {

    @Override
    public int compare(ODMForm form, ODMForm compareForm) {
        if (form.getStudy().getName().compareTo(compareForm.getStudy().getName()) != 0) {
            return form.getStudy().getName().compareTo(compareForm.getStudy().getName());
        } else if (form.getOID().compareTo(compareForm.getOID()) != 0) {
            return form.getOID().compareTo(compareForm.getOID());
        } else {
            return 1;
        }
    }
}
