package daft.handler;

import daft.filter.Filter;
import data.Action;
import data.ActionType;
import data.PropertyInfo;
import data.SearchMatchInfo;

import java.util.LinkedList;
import java.util.List;

public class PropertyChangeHandlerPlugin implements IPropertyInfoChangeHandler {

    protected final IFilterProvider filterProvider;

    protected static List<PropertyInfo> addedPropertyInfos;

    protected static SendMessageToProperty sendMessageToPropertyHandler = null;

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
                sendMessageToPropertyHandler = new SendMessageToProperty();
            }
        }
    }

    @Override
    public void propertyInfoAdded(PropertyInfo propertyInfo) {
        synchronized (this) {
            for(Filter filter : filterProvider.getFilters()) {
                if (filter.apply(propertyInfo.getFields())) {
                    addedPropertyInfos.add(propertyInfo);
                    for(Action action : filter.getActions()) {
                        SearchMatchInfo searchMatchInfo = new SearchMatchInfo(filter.getUser(), action, propertyInfo);
                        switch (action.getType()) {
                            case MESSAGE_ON_DAFT: {
                                sendMessageToPropertyHandler.handleNewMatch(searchMatchInfo);
                                break;
                            }
                        }
                    }
                }
            }
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
