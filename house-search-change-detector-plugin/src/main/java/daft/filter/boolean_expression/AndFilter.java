package daft.filter.boolean_expression;

import daft.filter.Filter;

import java.util.Map;

public class AndFilter extends BaseBooleanExpressionFilter {

    @Override
    public boolean apply(Map<String, String> fields) {
        boolean result = true;

        for (Filter filter: filters) {
            result = filter.apply(fields) && result;
            if (result == false) {
                return false;
            }
        }

        return result;
    }

}
