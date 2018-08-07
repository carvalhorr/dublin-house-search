package daft.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static Statement createDatbaseConnectionAndReturnStatement() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager
                .getConnection("jdbc:h2:/home/carvalhorr/carvalhorr@gmail.com/software/house-search/db/property;mv_store=false;AUTO_SERVER=TRUE", "sa", "");
        return connection.createStatement();
    }

    public static void createDatabase(Statement statement) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS property(id bigint, url varchar(50));");
        statement.execute("CREATE TABLE IF NOT EXISTS fields(property_id bigint, field_name varchar(50), field_value varchar(256));");
        statement.execute("CREATE UNIQUE INDEX IF NOT EXISTS PK_property_id ON PROPERTY(id);");
        statement.execute("CREATE INDEX IF NOT EXISTS FK_fields_property_id ON FIELDS(property_id);");
    }
}
