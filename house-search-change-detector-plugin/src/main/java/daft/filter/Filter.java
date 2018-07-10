package daft.filter;

import java.util.Map;

public abstract class Filter {

    protected String fieldName;
    protected ValueType fieldType;
    protected String value;

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

    public abstract boolean apply(Map<String, String> fields);

}
