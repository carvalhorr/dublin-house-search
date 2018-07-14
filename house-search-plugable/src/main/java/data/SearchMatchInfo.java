package data;

import java.util.List;

public class SearchMatchInfo {

    private User user;
    private List<ActionType> actions;
    private PropertyInfo property;

    public SearchMatchInfo(User user, List<ActionType> actions, PropertyInfo property) {
        this.user = user;
        this.actions = actions;
        this.property = property;
    }

    public User getUser() {
        return user;
    }

    public List<ActionType> getActions() {
        return actions;
    }

    public PropertyInfo getProperty() {
        return property;
    }
}
