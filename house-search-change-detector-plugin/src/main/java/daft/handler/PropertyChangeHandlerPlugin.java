package daft.handler;

import daft.filter.Filter;
import data.PropertyInfo;

import java.util.LinkedList;
import java.util.List;

public class PropertyChangeHandlerPlugin implements IPropertyInfoChangeHandler {

    protected final IFilterProvider filterProvider;

    protected static List<PropertyInfo> addedPropertyInfos;

    private int threadCounter = 0;

    public PropertyChangeHandlerPlugin() {
        filterProvider = new FilterProvider();
    }

    public PropertyChangeHandlerPlugin(IFilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @Override
    public void start() {
        synchronized (this) {
            threadCounter += 1;
            if (addedPropertyInfos == null) {
                addedPropertyInfos = new LinkedList<PropertyInfo>();
            }
        }
    }

    @Override
    public void propertyInfoAdded(PropertyInfo propertyInfo) {
        synchronized (this) {
            addedPropertyInfos.add(propertyInfo);
        }
    }

    @Override
    public void propertyInfoUpdated(PropertyInfo propertyInfo) {

    }

    @Override
    public void propertyInfoRemoved(PropertyInfo propertyInfo) {

    }

    @Override
    public void end() {
        synchronized (this) {
            threadCounter -= 1;
            if (threadCounter == 0) {
                System.out.println("Added properties:");
                for(PropertyInfo addedPropertyInfo : addedPropertyInfos) {
                    System.out.println("    " + addedPropertyInfo.toString());
                }
            }
        }

    }

}
