package daft.handler;

import daft.consistency.data.FieldConfig;
import daft.consistency.data.ProcessingType;
import daft.consistency.persistence.IFieldConfigPersistence;
import data.PropertyInfo;

import java.util.*;

public class DataConsistencyPropertyInfoHandler implements IPropertyInfoExtractedHandler {

    private static Map<String, FieldConfig> rentFields;
    private static Map<String, FieldConfig> shareFields;
    private static Map<String, FieldConfig> saleFields;

    private IFieldConfigPersistence fieldConfigPersistence;

    public DataConsistencyPropertyInfoHandler() {

    }

    public DataConsistencyPropertyInfoHandler(IFieldConfigPersistence fieldConfigPersistence) {
        this.fieldConfigPersistence = fieldConfigPersistence;
    }

    @Override
    public void start() {
        synchronized (this) {
            if (rentFields == null) {
                rentFields = loadFields(IFieldConfigPersistence.RENT_FIELDS);
                shareFields = loadFields(IFieldConfigPersistence.SHARE_FIELDS);
                saleFields = loadFields(IFieldConfigPersistence.SALE_FIELDS);

                cleanFields(rentFields);
                cleanFields(shareFields);
                cleanFields(saleFields);
            }
        }
    }

    @Override
    public void handle(PropertyInfo propertyInfo) {

        synchronized (this) {
            String type = propertyInfo.getFields().get("property_category");
            switch (type) {
                case "sale": {
                    addFields(saleFields, propertyInfo.getFields());
                    break;
                }
                case "rental": {
                    addFields(rentFields, propertyInfo.getFields());
                    break;
                }
                case "sharing": {
                    addFields(shareFields, propertyInfo.getFields());
                    break;
                }
            }
        }

    }

    private void addFields(Map<String, FieldConfig> fieldMap, Map<String, String> fields) {
        for (String fieldName : fields.keySet()) {
            String fieldValue = fields.get(fieldName);
            FieldConfig values = fieldMap.get(fieldName);
            if (values == null) {
                values = new FieldConfig();
                fieldMap.put(fieldName, values);
            }
            if (!ProcessingType.IGNORE.equals(values.getProcessingType())) {
                if (!values.getValues().contains(fieldValue)) {
                    values.getValues().add(fieldValue);
                }
            }
        }

    }

    @Override
    public void end() {
        synchronized (this) {
            if (rentFields != null) {
                saveFields(IFieldConfigPersistence.RENT_FIELDS, rentFields);
                saveFields(IFieldConfigPersistence.SHARE_FIELDS, shareFields);
                saveFields(IFieldConfigPersistence.SALE_FIELDS, saleFields);
                rentFields = null;
                shareFields = null;
                saleFields = null;
            }
        }
    }

    private void cleanFields(Map<String, FieldConfig> fields) {
        for(FieldConfig fieldConfig : fields.values()) {
            if (ProcessingType.IGNORE.equals(fieldConfig.getProcessingType())) {
                fieldConfig.setValues(new LinkedList<>());
            }
        }
    }

    private Map<String, FieldConfig> loadFields(String fieldSet) {
        return fieldConfigPersistence.loadFields(fieldSet);
    }

    private void saveFields(String fieldSet, Map<String, FieldConfig> fields) {
        fieldConfigPersistence.saveFields(fieldSet, fields);
    }

}
