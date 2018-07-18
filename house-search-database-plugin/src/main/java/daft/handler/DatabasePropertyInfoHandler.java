package daft.handler;

import daft.persistence.PropertyInfoPersistence;
import data.PropertyInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabasePropertyInfoHandler implements IPropertyInfoExtractedHandler {

    private int counter = 0;

    private Statement statement;
    private PropertyInfoPersistence persistence;

    private final IPropertyInfoChangeHandler changeHandler;

    public DatabasePropertyInfoHandler() {
        changeHandler = new PropertyChangeHandlerPlugin();
    }

    public DatabasePropertyInfoHandler(IPropertyInfoChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    @Override
    public void start() {

        try {

            Connection connection = DriverManager
                    .getConnection("jdbc:h2:/home/carvalhorr/carvalhorr@gmail.com/software/house-search/db/property;mv_store=false;AUTO_SERVER=TRUE", "sa", "");
            statement = connection.createStatement();
            persistence = new PropertyInfoPersistence(changeHandler, statement);
            persistence.createTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        changeHandler.start();

    }

    @Override
    public void handle(PropertyInfo propertyInfo) {
        if (statement != null) {
            try {

                persistence.processPropertyInfo(propertyInfo);
                counter ++;
                if (counter%200 == 0) {
                    counter = 0;
                    statement.getConnection().commit();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void end() {

        changeHandler.end();

        if (statement != null) {
            try {

                Connection connection = statement.getConnection();
                connection.commit();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
