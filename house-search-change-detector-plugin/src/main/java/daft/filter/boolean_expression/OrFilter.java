package daft.filter.boolean_expression;

import daft.filter.Filter;

import java.util.Map;

public class OrFilter extends BaseBooleanExpressionFilter {

    @Override
    public boolean apply(Map<String, String> fields) {
        boolean result = false;

        for (Filter filter: filters) {
            result = result || filter.apply(fields);
            if (result == true) {
                return true;
            }
        }

        return result;
    }

}
