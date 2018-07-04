package daft.handler;

import data.PropertyInfo;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class PropertyInfoHandlerService implements IPropertyInfoExtractedHandler {

    private ServiceLoader<IPropertyInfoExtractedHandler> loader;

    public PropertyInfoHandlerService() {
        loader = ServiceLoader.load(IPropertyInfoExtractedHandler.class);
    }

    @Override
    public void start() {
        try {
            Iterator<IPropertyInfoExtractedHandler> handlers = loader.iterator();
            while (handlers.hasNext()) {
                IPropertyInfoExtractedHandler handler = handlers.next();
                handler.start();
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }
    }

    @Override
    public void handle(PropertyInfo propertyInfo) {
        try {
            Iterator<IPropertyInfoExtractedHandler> handlers = loader.iterator();
            while (handlers.hasNext()) {
                IPropertyInfoExtractedHandler handler = handlers.next();
                handler.handle(propertyInfo);
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }

    }

    @Override
    public void end() {
        try {
            Iterator<IPropertyInfoExtractedHandler> handlers = loader.iterator();
            while (handlers.hasNext()) {
                IPropertyInfoExtractedHandler handler = handlers.next();
                handler.end();
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }

    }
}
