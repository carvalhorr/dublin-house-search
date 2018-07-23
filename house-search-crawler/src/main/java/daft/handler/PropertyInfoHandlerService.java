package daft.handler;

import data.PropertyInfo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PropertyInfoHandlerService implements IPropertyInfoExtractedHandler {

    private ServiceLoader<IPropertyInfoExtractedHandler> loader;


    public PropertyInfoHandlerService() {
        File[] pluginFiles = new File("plugins").listFiles((File dir, String name) -> name.endsWith(".jar"));
        List<URL> urlsList = new ArrayList<>();
        for (File file : pluginFiles) {
            try {
                urlsList.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        URLClassLoader classLoader = URLClassLoader.newInstance(urlsList.toArray(new URL[]{}), Thread.currentThread().getContextClassLoader());
        loader = ServiceLoader.load(IPropertyInfoExtractedHandler.class,
                classLoader);
    }


    @Override
    public void start() {
        try {
            loader.reload();
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
