package daft.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public class ComparatorUtil {

    public static boolean isGreaterThan(Object valueA, Object valueB) {
        if(valueA instanceof Integer) {
            return isGreaterThan((Integer) valueA, (Integer) valueB);
        }
        if (valueA instanceof String) {
            return isGreaterThan((String) valueA, (String) valueB);
        }
        if (valueA instanceof Boolean) {
            return isGreaterThan((Boolean) valueA, (Boolean) valueB);
        }
        if (valueA instanceof List) {
            return isGreaterThan((List) valueA, (List) valueB);
        }
        throw new IllegalArgumentException("Can't compare objects of this type.");
    }

    public static boolean isGreaterThan(Integer valueA, Integer valueB) {
        return valueA > valueB;
    }

    public static boolean isGreaterThan(String valueA, String valueB) {
        return valueA.compareTo(valueB) > 0;
    }

    public static boolean isGreaterThan(Boolean valueA, Boolean valueB) {
        throw new IllegalArgumentException("Can't compare boolean values.");
    }

    public static boolean isGreaterThan(List valueA, List valueB) {
        throw new IllegalArgumentException("Can't compare list values.");
    }

    public static boolean isLessThan(Object valueA, Object valueB) {
        if(valueA instanceof Integer) {
            return isLessThan((Integer) valueA, (Integer) valueB);
        }
        if (valueA instanceof String) {
            return isLessThan((String) valueA, (String) valueB);
        }
        if (valueA instanceof Boolean) {
            return isLessThan((Boolean) valueA, (Boolean) valueB);
        }
        if (valueA instanceof List) {
            return isLessThan((List) valueA, (List) valueB);
        }
        throw new IllegalArgumentException("Can't compare objects of this type.");
    }

    public static boolean isLessThan(Integer valueA, Integer valueB) {
        return valueA < valueB;
    }

    public static boolean isLessThan(String valueA, String valueB) {
        return valueA.compareTo(valueB) < 0;
    }

    public static boolean isLessThan(Boolean valueA, Boolean valueB) {
        throw new IllegalArgumentException("Can't compare boolean values.");
    }

    public static boolean isLessThan(List valueA, List valueB) {
        throw new IllegalArgumentException("Can't compare list values.");
    }

}
