package daft.handler;

import data.PropertyInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldExtractorPropertyInfoHandler implements IPropertyInfoExtractedHandler {

    private static Map<String, List<String>> rentFields;
    private static Map<String, List<String>> shareFields;
    private static Map<String, List<String>> saleFields;

    @Override
    public void start() {
        if (rentFields == null) {
            rentFields = new HashMap<>();
            shareFields = new HashMap<>();
            saleFields = new HashMap<>();
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

    private void addFields(Map<String, List<String>> fieldMap, Map<String, String> fields) {
        for (String fieldName : fields.keySet()) {
            String fieldValue = fields.get(fieldName);
            List<String> values = fieldMap.get(fieldName);
            if (values == null) {
                values = new ArrayList<>();
                fieldMap.put(fieldName, values);
            }
            if (!values.contains(fieldValue)) {
                values.add(fieldValue);
            }
        }

    }

    @Override
    public void end() {
        System.out.println("Rent: ");
        printStatistics(rentFields);
        System.out.println("Share: ");
        printStatistics(shareFields);
        System.out.println("Sale: ");
        printStatistics(saleFields);
    }

    private void printStatistics(Map<String, List<String>> fields) {
        for (String fieldName : fields.keySet()) {
            System.out.println("  " + fieldName + " : " + fields.get(fieldName));
        }
    }
}
