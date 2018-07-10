package daft.handler;

import data.PropertyInfo;

public interface IPropertyInfoChangeHandler {

    void start();

    void propertyInfoAdded(PropertyInfo propertyInfo);
    void propertyInfoUpdated(PropertyInfo propertyInfo);
    void propertyInfoRemoved(PropertyInfo propertyInfo);

    void end();

}
