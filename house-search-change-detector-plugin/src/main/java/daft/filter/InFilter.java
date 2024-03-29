package daft.filter;

import daft.util.FieldValueConverter;
import data.ValueType;

import java.util.List;
import java.util.Map;

public class InFilter extends Filter {

    @Override
    public boolean apply(Map<String, String> fields) {
        return ((List) FieldValueConverter.convertValue(ValueType.LIST, value))
                .contains(fields.get(fieldName));
    }

}
