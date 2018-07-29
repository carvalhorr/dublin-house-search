package daft.consistency.persistence;

import daft.consistency.data.FieldConfig;
import daft.consistency.data.ProcessingType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldConfigPersistenceTests {

    @Test
    public void testSaveFields() {

    }

    @Test
    public void testLoadFields() {

        Map<String, FieldConfig> fields = new HashMap<String, FieldConfig>() {{
            put("field",
                    new FieldConfig() {
                        {
                            setFieldName("field");
                            setProcessingType(ProcessingType.MONITOR);
                            setValues(new ArrayList<String>() {{
                                add("value1");
                                add("value2");
                            }});
                        }
                    });
            put("property_category",
                    new FieldConfig() {
                        {
                            setFieldName("property_category");
                            setProcessingType(ProcessingType.MONITOR);
                            setValues(new ArrayList<String>() {{
                                add("rental");
                            }});
                        }
                    });
        }};

        FieldConfigPersistence persistence = new FieldConfigPersistence();
        persistence.saveFields(IFieldConfigPersistence.SHARE_FIELDS, fields);

    }

}
