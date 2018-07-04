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
            int type = getType(propertyInfo.getUrl());
            switch (type) {
                case 1: {
                    addFields(saleFields, propertyInfo.getAdditionalInfo());
                    break;
                }
                case 2: {
                    addFields(rentFields, propertyInfo.getAdditionalInfo());
                    break;
                }
                case 3: {
                    addFields(shareFields, propertyInfo.getAdditionalInfo());
                    break;
                }
            }
        }

    }

    private int getType(String url) {
        String parts[] = url.split("/");
        String lastPart = parts[parts.length - 1];
        return Integer.parseInt(lastPart.substring(0, 1));
    }

    private void addFields(Map<String, List<String>> fieldMap, String fieldsString) {
        String fields[] = fieldsString
                .replace("{", "")
                .replace("}", "")
                .split(",");
        for(String field : fields) {
            String fieldPair[] = field.split(":");
            String fieldName = fieldPair[0].trim();
            String fieldValue = "";
            if (fieldPair.length == 2) {
                fieldValue = fieldPair[1].trim();
            }
            if (fieldMap.containsKey(fieldName)) {
                List<String> fieldValues = fieldMap.get(fieldName);
                if (!fieldValues.contains(fieldValue)) {
                    fieldValues.add(fieldValue);
                }
            } else {
                List<String> fieldValues = new ArrayList<>();
                fieldValues.add(fieldValue);
                fieldMap.put(fieldName, fieldValues);
            }
        }
    }

    @Override
    public void end() {
        System.out.println("Rent: " + rentFields.toString());
        System.out.println("Share: " + shareFields.toString());
        System.out.println("Sale: " + saleFields.toString());
    }
}
