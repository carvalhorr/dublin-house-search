package daft.handler;

import data.Action;
import data.SearchMatchInfo;

public interface ISearchMatchHandler {

    void handleNewMatch(SearchMatchInfo matchInfo, Action action);

}
