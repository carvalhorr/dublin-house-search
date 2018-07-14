package daft.handler;

import daft.filter.Filter;

import java.util.List;

public interface IFilterProvider {
    public List<Filter> getFilters();
}
