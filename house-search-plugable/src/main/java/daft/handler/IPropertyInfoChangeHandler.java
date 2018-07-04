package daft.handler;

import data.PropertyInfo;

public interface IPropertyInfoChangeHandler {

    void PropertyInfoAdded(PropertyInfo propertyInfo);
    void PropertyInfoUpdated(PropertyInfo propertyInfo);
    void PropertyInfoRemoved(PropertyInfo propertyInfo);

}
