package daft.consistency.persistence;

import daft.consistency.data.FieldConfig;

import java.util.HashMap;
import java.util.Map;

public interface IFieldConfigPersistence {

    public static final String RENT_FIELDS = "rent_fields";
    public static final String SHARE_FIELDS = "share_fields";
    public static final String SALE_FIELDS = "sale_fields";

    Map<String, FieldConfig> loadFields(String fieldSet);
    void saveFields(String fieldSet, Map<String, FieldConfig> fields);

}
