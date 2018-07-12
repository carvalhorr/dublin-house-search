package daft.filter;

import daft.util.ComparatorUtil;
import daft.util.FieldValueConverter;

import java.util.Map;

public class GreaterThanFilter extends Filter {

    @Override
    public boolean apply(Map<String, String> fields) {
        return ComparatorUtil.isGreaterThan(FieldValueConverter.convertValue(getFieldType(), fields.get(getFieldName()))
                , FieldValueConverter.convertValue(getFieldType(), getValue()));
    }

}
