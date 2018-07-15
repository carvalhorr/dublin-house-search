package daft.filter;

import data.Action;
import data.ActionType;
import data.User;

import java.util.List;
import java.util.Map;

public abstract class Filter {

    protected String fieldName;
    protected ValueType fieldType;
    protected String value;

    protected User user;
    protected List<Action> actions;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public ValueType getFieldType() {
        return fieldType;
    }

    public void setFieldType(ValueType fieldType) {
        this.fieldType = fieldType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public abstract boolean apply(Map<String, String> fields);



}
