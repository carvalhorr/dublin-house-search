package daft.handler;

import data.SearchMatchInfo;

public interface ISearchMatchHandler {

    void handleNewMatch(SearchMatchInfo matchInfo);

}
