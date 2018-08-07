package daft.persistence;

import daft.handler.IPropertyInfoChangeHandler;
import data.PropertyInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class PropertyInfoPersistence {

    private final Statement statement;
    private final IPropertyInfoChangeHandler changeHandler;

    public PropertyInfoPersistence(IPropertyInfoChangeHandler changeHandler,
                                   Statement statement) {
        this.statement = statement;
        this.changeHandler = changeHandler;
    }

    public void processPropertyInfo(PropertyInfo propertyInfo) {
        try {
            if (propertyInfo.isRemoved()) {
                delete(propertyInfo);
                if (changeHandler != null) {
                    changeHandler.propertyInfoRemoved(propertyInfo);
                }
            } else {
                if (isNew(propertyInfo)) {
                    add(propertyInfo);
                    if (changeHandler != null) {
                        changeHandler.propertyInfoAdded(propertyInfo);
                    }
                } else {
                    update(propertyInfo);
                    if (changeHandler != null) {
                        changeHandler.propertyInfoUpdated(propertyInfo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isNew(PropertyInfo propertyInfo) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT COUNT(id) FROM property WHERE id = '" + propertyInfo.getId() + "';");
        result.next();
        if (result.getInt(1) > 0) {
            return false;
        } else {
            return true;
        }
    }

    private void add(PropertyInfo propertyInfo) throws SQLException {
        statement.execute("INSERT INTO property(id, url) VALUES('" +
                propertyInfo.getId() + "', '" +
                propertyInfo.getUrl() + "');");
        insertFields(propertyInfo.getId(), propertyInfo.getFields());
    }

    private void update(PropertyInfo propertyInfo) throws SQLException {
        deleteFields(propertyInfo.getId());
        insertFields(propertyInfo.getId(), propertyInfo.getFields());
    }

    private void delete(PropertyInfo propertyInfo) throws SQLException {
        deleteFields(propertyInfo.getId());
        statement.execute("DELETE FROM property WHERE id = '" + propertyInfo.getId() + "';");
    }

    private void deleteFields(String id) throws SQLException {
        statement.execute("DELETE FROM fields WHERE property_id = '" + id + "';");
    }

    private void insertFields(String propertyId, Map<String, String> fields) throws SQLException {
        for (Map.Entry<String, String> field : fields.entrySet()) {
            statement.execute("INSERT INTO fields(property_id, field_name, field_value) VALUES('" +
                    propertyId + "', '" +
                    escapeValue(field.getKey()) + "', '" +
                    escapeValue(field.getValue()) + "');");
        }
    }

    private String escapeValue(String value) {
        return value.replace("'", "''");
    }

}
