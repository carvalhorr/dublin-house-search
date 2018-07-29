package daft.handler;

import daft.consistency.data.FieldConfig;
import daft.consistency.data.ProcessingType;
import daft.consistency.persistence.FieldConfigPersistence;
import daft.consistency.persistence.IFieldConfigPersistence;
import data.PropertyInfo;

import java.util.*;

public class DataConsistencyPropertyInfoHandler implements IPropertyInfoExtractedHandler {

    private static boolean finished = false;

    private static Map<String, FieldConfig> rentFields;
    private static Map<String, FieldConfig> shareFields;
    private static Map<String, FieldConfig> saleFields;

    private IFieldConfigPersistence fieldConfigPersistence;

    public DataConsistencyPropertyInfoHandler() {
        fieldConfigPersistence = new FieldConfigPersistence();
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
                values.setFieldName(fieldName);
                fieldMap.put(fieldName, values);
            }
            if (!ProcessingType.IGNORE.equals(values.getProcessingType())) {
                switch (values.getValueType()) {
                    case LIST: {
                        String[] fieldValueParts = fieldValue.split(",");
                        for (String fieldValuePart : fieldValueParts) {
                            addValue(values.getValues(), fieldValuePart.trim());
                        }
                        break;
                    }
                    case YESNO:
                    case STRING:
                    case BOOLEAN:
                    case INTEGER: {
                        addValue(values.getValues(), fieldValue);
                        break;
                    }
                }
            }
        }

    }

    private void addValue(List<String> values, String value) {
        if (!values.contains(value)) {
            values.add(value);
        }
    }

    @Override
    public void end() {

        synchronized (this) {
            if (!finished) {
                System.out.println("writing files");
                saveFields(IFieldConfigPersistence.RENT_FIELDS, rentFields);
                saveFields(IFieldConfigPersistence.SHARE_FIELDS, shareFields);
                saveFields(IFieldConfigPersistence.SALE_FIELDS, saleFields);
                finished = true;
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
