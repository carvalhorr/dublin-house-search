package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyInfo {

    private String id;
    private String url;
    private String advertiserName;
    private List<String> advertiserPhoneNumbers;
    private boolean removed;

    private Map<String, String> fields = new HashMap<>();

    public PropertyInfo(String id,
                        String url,
                        String additionalInfo) {
        this.id = id;
        this.url = url;
        processAdditionalInfo(additionalInfo);
        this.removed = false;
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

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }

    public List<String> getAdvertiserPhoneNumbers() {
        return advertiserPhoneNumbers;
    }

    public void setAdvertiserPhoneNumbers(List<String> advertiserPhoneNumbers) {
        this.advertiserPhoneNumbers = advertiserPhoneNumbers;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    private void processAdditionalInfo(String additionalInfo) {
        String fieldsString = additionalInfo
                .replace("{", "")
                .replace("}", "") + ",";
        int x = 0;
        boolean take = true;
        for (int i = 0; i < fieldsString.length(); i++) {
            if (fieldsString.charAt(i) == '"') {
                take = !take;
            } else if (fieldsString.charAt(i) == ',') {
                if (take) {
                    String field = fieldsString.substring(x, i).trim();
                    if (field.startsWith(",")) {
                        field = field.substring(1, field.length()).trim();
                    }
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
