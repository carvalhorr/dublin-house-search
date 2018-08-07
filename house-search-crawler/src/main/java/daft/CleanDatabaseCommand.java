package daft;

import daft.handler.DatabasePropertyInfoHandler;
import daft.persistence.Database;
import daft.persistence.PropertyInfoPersistence;
import data.PropertyInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CleanDatabaseCommand {

    private Statement statement;
    private DatabasePropertyInfoHandler handler;

    public static void main(String[] args) throws SQLException {

        Statement statement = Database.createDatbaseConnectionAndReturnStatement();
        Database.createDatabase(statement);

        DatabasePropertyInfoHandler handler = new DatabasePropertyInfoHandler();
        handler.start();

        CleanDatabaseCommand command = new CleanDatabaseCommand(statement, handler);
        command.cleanDatabase();
    }

    public CleanDatabaseCommand(Statement statement, DatabasePropertyInfoHandler handler) {
        this.statement = statement;
        this.handler = handler;
    }

    private void cleanDatabase() throws SQLException {
        PropertyInfoExtractor extractor = new PropertyInfoExtractor();
        ResultSet result = queryAllProperties();
        while(result.next()) {
            int id = result.getInt(1) ;
            System.out.print(id + ":");
            PropertyInfo propertyInfo = extractor.extractPropertyInfo("" + id);
            if (propertyInfo != null) {
                if (propertyInfo.isRemoved()) {
                    System.out.print(" REMOVED");
                    // handler.handle(propertyInfo);
                }
            } else {
                System.out.print(" not found");
            }
            System.out.println("");
        }
    }

    private ResultSet queryAllProperties() throws SQLException {
        return statement.executeQuery("SELECT id FROM property;");
    }

}
