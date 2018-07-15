package daft.handler;

import daft.filter.Filter;
import daft.filter.Search;
import data.Action;
import data.PropertyInfo;
import data.SearchMatchInfo;

import java.util.LinkedList;
import java.util.List;

public class PropertyChangeHandlerPlugin implements IPropertyInfoChangeHandler {

    protected final ISearchProvider filterProvider;

    protected static List<PropertyInfo> addedPropertyInfos;

    protected static SendMessageToProperty sendMessageToPropertyHandler = null;
    protected static SendEmailNewProperty sendEmailNewProperty = null;

    private int threadCounter = 0;

    public PropertyChangeHandlerPlugin() {
        filterProvider = new FilterProvider();
    }

    public PropertyChangeHandlerPlugin(ISearchProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @Override
    public void start() {
        synchronized (this) {
            threadCounter += 1;
            if (addedPropertyInfos == null) {
                addedPropertyInfos = new LinkedList<PropertyInfo>();
                sendMessageToPropertyHandler = new SendMessageToProperty();
                sendEmailNewProperty = new SendEmailNewProperty();
            }
        }
    }

    @Override
    public void propertyInfoAdded(PropertyInfo propertyInfo) {
        synchronized (this) {
            addedPropertyInfos.add(propertyInfo);
            for(Search search : filterProvider.getSearches()) {
                if (search.getFilter().apply(propertyInfo.getFields())) {
                    for(Action action : search.getActions()) {
                        SearchMatchInfo searchMatchInfo = new SearchMatchInfo(search.getName(),
                                search.getUser(),
                                action,
                                propertyInfo);
                        switch (action.getType()) {
                            case MESSAGE_ON_DAFT: {
                                sendMessageToPropertyHandler.handleNewMatch(searchMatchInfo);
                                break;
                            }
                            case EMAIL_INDIVIDUAL: {
                                sendEmailNewProperty.handleNewMatch(searchMatchInfo);
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
