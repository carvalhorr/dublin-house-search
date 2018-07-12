package daft.util;

import daft.filter.ValueType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FieldValueConverter {

    public static Object convertValue(ValueType type, String value) {
        switch (type) {
            case INTEGER: {
                return Integer.parseInt(value);
            }
            case BOOLEAN: {
                return Boolean.parseBoolean(value);
            }
            case YESNO: {
                if ("YES".equals(value.toUpperCase())) {
                    return true;
                }
                if ("NO".equals(value.toUpperCase())) {
                    return false;
                }
                throw new IllegalArgumentException("Value '" + value + "' not in valid range for YesNo field.");
            }
            case LIST: {
                return Arrays.asList(value.split(","))
                        .stream()
                        .map(part->part.trim())
                        .collect(Collectors.toList());
            }
            case STRING: {
                return value;
            }
        }
        throw new IllegalArgumentException("Unexpected value type: " + type.toString());
    }

}
