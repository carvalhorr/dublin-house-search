package daft.handler;

import data.PropertyInfo;

public class ConsolePrinterPropertyInfoHandler implements IPropertyInfoExtractedHandler {

    @Override
    public void start() {

    }

    @Override
    public void handle(PropertyInfo propertyInfo) {
        System.out.println("    " + propertyInfo);
    }

    @Override
    public void end() {

    }

}
