package de.imi.odmtoolbox.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration represents the compare types used for data item comparison.
 *
 */
public enum ODMCompareType {

    IDENTICAL("identical"),
    MATCHING("matching"),
    TRANSFORMABLE("transformable"),
    SIMILAR("similar"),
    COMPARABLE("comparable"),
    DIFFERENT("different"),
    NOTCODED("notcoded");

    private String textValue;
    private static final Map<String, ODMCompareType> stringToEnum = new HashMap<>();

    static {
        for (ODMCompareType cValue : values()) {
            stringToEnum.put(cValue.toString(), cValue);
        }
    }

    ODMCompareType(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }

    @Override
    public String toString() {
        return textValue;
    }

    public static ODMCompareType fromString(String textValue) {
        return stringToEnum.get(textValue);
    }
}
