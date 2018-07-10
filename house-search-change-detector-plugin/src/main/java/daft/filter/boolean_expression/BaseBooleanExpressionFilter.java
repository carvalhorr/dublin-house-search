package daft.filter.boolean_expression;

import daft.filter.Filter;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseBooleanExpressionFilter extends Filter {

    protected List<Filter> filters = new LinkedList<Filter>();

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
