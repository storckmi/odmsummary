package de.imi.odmtoolbox.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration represents the data types from the ODM standard.
 *
 */
public enum ODMItemDataType {

    STRING("string"),
    TEXT("text"),
    BOOLEAN("boolean"),
    INTEGER("integer"),
    FLOAT("float"),
    HEXFLOAT("hexFloat"),
    BASE64FLOAT("base64Float"),
    DOUBLE("double"),
    DATE("date"),
    PARTIALDATE("partialDate"),
    INCOMPLETEDATE("incompleteDate"),
    TIME("time"),
    PARTIALTIME("partialTime"),
    INCOMPLETETIME("incompleteTime"),
    DATETIME("datetime"),
    PARTIALDATETIME("partialDatetime"),
    DURATIONDATETIME("durationDatetime"),
    INTERVALDATETIME("intervalDatetime"),
    INCOMPLETEDATETIME("incompleteDatetime"),
    HEXBINARY("hexBinary"),
    BASE64BINARY("base64Binary"),
    URI("URI");

    private String textValue;
    private static final Map<String, ODMItemDataType> stringToEnum = new HashMap<>();

    static {
        for (ODMItemDataType cValue : values()) {
            stringToEnum.put(cValue.toString(), cValue);
        }
    }

    ODMItemDataType(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }

    @Override
    public String toString() {
        return textValue;
    }

    public static ODMItemDataType fromString(String textValue) {
        return stringToEnum.get(textValue);
    }
}
