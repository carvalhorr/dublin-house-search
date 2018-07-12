package daft.filter;

import daft.filter.boolean_expression.OrFilter;

import java.util.Map;

public class LessThanOrEqualFilter extends Filter {

    private OrFilter orFilter = null;

    @Override
    public boolean apply(Map<String, String> fields) {

        if (orFilter == null) {
            orFilter = new OrFilter();

            EqualsFilter equalsFilter = new EqualsFilter();
            equalsFilter.setFieldType(getFieldType());
            equalsFilter.setFieldName(getFieldName());
            equalsFilter.setValue(getValue());
            orFilter.getFilters().add(equalsFilter);

            LessThanFilter greaterFilter = new LessThanFilter();
            greaterFilter.setFieldType(getFieldType());
            greaterFilter.setFieldName(getFieldName());
            greaterFilter.setValue(getValue());
            orFilter.getFilters().add(greaterFilter);

        }

        return orFilter.apply(fields);
    }

}
