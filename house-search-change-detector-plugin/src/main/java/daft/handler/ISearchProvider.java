package daft.handler;

import daft.filter.Filter;
import daft.filter.Search;

import java.util.List;

public interface ISearchProvider {
    public List<Search> getSearches();
}
