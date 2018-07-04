package daft.persistence;

import data.PropertyInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PropertyInfoPersistence {

    private Statement statement;

    public PropertyInfoPersistence(Statement statement) {
        this.statement = statement;
    }

    public void createTable() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS property(name varchar(100));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processPropertyInfo(PropertyInfo propertyInfo) {
        try {
            if (isNew(propertyInfo)) {
                add(propertyInfo);
            } else {
                update(propertyInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isNew(PropertyInfo propertyInfo) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT COUNT(id) from PROPERTY where id = '" + propertyInfo.getId() + "';");
        if (result.getInt(1) > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void add(PropertyInfo propertyInfo) throws SQLException {
        statement.execute("insert into property(name) values('"
                + propertyInfo.getName().replace("'", "''") + "');");
    }

    private void update(PropertyInfo propertyInfo) throws SQLException {
        statement.execute("insert into property(name) values('"
                + propertyInfo.getName().replace("'", "''") + "');");
    }

}
