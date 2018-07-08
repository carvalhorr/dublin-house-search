package data;

import java.util.HashMap;
import java.util.Map;

public class PropertyInfo {

    private String id;
    private String url;
    private Map<String, String> fields = new HashMap<>();

    public PropertyInfo(String id,
                        String url,
                        String additionalInfo) {
        this.id = id;
        this.url = url;
        processAdditionalInfo(additionalInfo);
    }


    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getFields() {
        return fields;
    }
    private void processAdditionalInfo(String additionalInfo) {
        String fieldsString = additionalInfo
                .replace("{", "")
                .replace("}", "");
        int x = 0;
        boolean take = true;
        for (int i = 0; i < fieldsString.length(); i++) {
            if (fieldsString.charAt(i) == '"') {
                take = !take;
            } else if (fieldsString.charAt(i) == ',') {
                if (take) {
                    String field = fieldsString.substring(x, i);
                    // System.out.println(field);
                    String fieldPair[] = field.split(":");
                    String fieldName = fieldPair[0].trim();
                    String fieldValue = "";
                    if (fieldPair.length == 2) {
                        fieldValue = fieldPair[1].trim();
                    }
                    fields.put(fieldName
                                    .replace(",", "")
                                    .replace("\"", ""),
                            fieldValue
                                    .replace("\"", ""));
                    x = i;
                }
            }
        }

    }

    @Override
    public String toString() {
        return "PropertyInfo{" +
                "id='" + id + "\'" +
                ", url='" + url + '\'' +
                ", addtionalInfo=" + fields.toString() +
                '}';
    }

}
