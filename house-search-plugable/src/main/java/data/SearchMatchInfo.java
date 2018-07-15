package data;

import java.util.List;

public class SearchMatchInfo {

    private User user;
    private Action action;
    private PropertyInfo property;

    public SearchMatchInfo(User user, Action action, PropertyInfo property) {
        this.user = user;
        this.action = action;
        this.property = property;
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
