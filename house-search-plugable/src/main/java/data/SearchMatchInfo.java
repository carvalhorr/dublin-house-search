package data;

import java.util.List;

public class SearchMatchInfo {

    private String searchName;
    private User user;
    private Action action;
    private PropertyInfo property;

    public SearchMatchInfo(String searchName, User user, Action action, PropertyInfo property) {
        this.searchName = searchName;
        this.user = user;
        this.action = action;
        this.property = property;
    }

    public String getSearchName() {
        return searchName;
    }

    public User getUser() {
        return user;
    }

    public Action getAction() {
        return action;
    }

    public PropertyInfo getProperty() {
        return property;
    }
}
