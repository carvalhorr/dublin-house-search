package daft.filter;

import java.util.Map;

public class NotEqualsFilter extends Filter {

    @Override
    public boolean apply(Map<String, String> fields) {
        return !FieldValueConverter.convertValue(fieldType, value)
                .equals(FieldValueConverter.convertValue(fieldType, fields.get(fieldName)));
    }

}
