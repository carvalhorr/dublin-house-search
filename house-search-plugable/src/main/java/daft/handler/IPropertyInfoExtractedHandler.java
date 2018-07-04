package daft.handler;

import data.PropertyInfo;

public interface IPropertyInfoExtractedHandler {
    void start();
    void handle(PropertyInfo propertyInfo);
    void end();
}
