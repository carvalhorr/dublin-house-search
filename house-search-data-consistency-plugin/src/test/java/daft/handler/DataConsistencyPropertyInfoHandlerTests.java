package daft.handler;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import daft.consistency.data.FieldConfig;
import daft.consistency.data.ProcessingType;
import daft.consistency.persistence.IFieldConfigPersistence;
import data.PropertyInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(DataProviderRunner.class)
public class DataConsistencyPropertyInfoHandlerTests {

    @DataProvider(format = "%m: %p[0]")
    public static Object[][] data() {
        return new Object[][]{
                {
                        "add one rental property with one single value in \"field\"",
                        new PropertyInfo[]{
                                new PropertyInfo(
                                        "1",
                                        "http://daft.ir/1",
                                        "{\"property_category\": \"rental\", \"field\":\"value1\"}")
                        },
                        new HashMap<String, Map<String, FieldConfig>>() {
                            {
                                put(IFieldConfigPersistence.RENT_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value1");
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
                                        }});
                                put(IFieldConfigPersistence.SALE_FIELDS,
                                        new HashMap<String, FieldConfig>());
                                put(IFieldConfigPersistence.SHARE_FIELDS,
                                        new HashMap<String, FieldConfig>());

                            }

                        }
                },
                {
                        "add two rental properties with two different values in \"field\"",
                        new PropertyInfo[]{
                                new PropertyInfo(
                                        "1",
                                        "http://daft.ie/1",
                                        "{\"property_category\": \"rental\", \"field\":\"value1\"}"),
                                new PropertyInfo(
                                        "2",
                                        "http://daft.ie/2",
                                        "{\"property_category\": \"rental\", \"field\":\"value2\"}")

                        },
                        new HashMap<String, Map<String, FieldConfig>>() {
                            {
                                put(IFieldConfigPersistence.RENT_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
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
                                        }});
                                put(IFieldConfigPersistence.SALE_FIELDS,
                                        new HashMap<String, FieldConfig>());
                                put(IFieldConfigPersistence.SHARE_FIELDS,
                                        new HashMap<String, FieldConfig>());

                            }

                        }
                },
                {
                        "add two rental properties with one single value in \"field\"",
                        new PropertyInfo[]{
                                new PropertyInfo(
                                        "1",
                                        "http://daft.ie/1",
                                        "{\"property_category\": \"rental\", \"field\":\"value1\"}"),
                                new PropertyInfo(
                                        "2",
                                        "http://daft.ie/2",
                                        "{\"property_category\": \"rental\", \"field\":\"value1\"}")

                        },
                        new HashMap<String, Map<String, FieldConfig>>() {
                            {
                                put(IFieldConfigPersistence.RENT_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value1");
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
                                        }});
                                put(IFieldConfigPersistence.SALE_FIELDS,
                                        new HashMap<String, FieldConfig>());
                                put(IFieldConfigPersistence.SHARE_FIELDS,
                                        new HashMap<String, FieldConfig>());

                            }

                        }
                },
                {
                        "add one rental property and one sale property with same value in \"field\"",
                        new PropertyInfo[]{
                                new PropertyInfo(
                                        "1",
                                        "http://daft.ie/1",
                                        "{\"property_category\": \"sale\", \"field\":\"value1\"}"),
                                new PropertyInfo(
                                        "2",
                                        "http://daft.ie/2",
                                        "{\"property_category\": \"rental\", \"field\":\"value1\"}")

                        },
                        new HashMap<String, Map<String, FieldConfig>>() {
                            {
                                put(IFieldConfigPersistence.RENT_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value1");
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
                                        }});
                                put(IFieldConfigPersistence.SALE_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value1");
                                                            }});
                                                        }
                                                    });
                                            put("property_category",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("property_category");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("sale");
                                                            }});
                                                        }
                                                    });
                                        }});
                                put(IFieldConfigPersistence.SHARE_FIELDS,
                                        new HashMap<String, FieldConfig>());

                            }

                        }
                },
                {
                        "add one rental property and one sale property with different values in \"field\"",
                        new PropertyInfo[]{
                                new PropertyInfo(
                                        "1",
                                        "http://daft.ie/1",
                                        "{\"property_category\": \"sale\", \"field\":\"value1\"}"),
                                new PropertyInfo(
                                        "2",
                                        "http://daft.ie/2",
                                        "{\"property_category\": \"rental\", \"field\":\"value2\"}")

                        },
                        new HashMap<String, Map<String, FieldConfig>>() {
                            {
                                put(IFieldConfigPersistence.RENT_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
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
                                        }});
                                put(IFieldConfigPersistence.SALE_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value1");
                                                            }});
                                                        }
                                                    });
                                            put("property_category",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("property_category");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("sale");
                                                            }});
                                                        }
                                                    });
                                        }});
                                put(IFieldConfigPersistence.SHARE_FIELDS,
                                        new HashMap<String, FieldConfig>());

                            }

                        }
                },
                {
                        "add one rental property, one sale property and one share property with same value in \"field\"",
                        new PropertyInfo[]{
                                new PropertyInfo(
                                        "1",
                                        "http://daft.ie/1",
                                        "{\"property_category\": \"sale\", \"field\":\"value1\"}"),
                                new PropertyInfo(
                                        "2",
                                        "http://daft.ie/2",
                                        "{\"property_category\": \"rental\", \"field\":\"value2\"}"),
                                new PropertyInfo(
                                        "2",
                                        "http://daft.ie/2",
                                        "{\"property_category\": \"sharing\", \"field\":\"value3\"}")

                        },
                        new HashMap<String, Map<String, FieldConfig>>() {
                            {
                                put(IFieldConfigPersistence.RENT_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
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
                                        }});
                                put(IFieldConfigPersistence.SALE_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value1");
                                                            }});
                                                        }
                                                    });
                                            put("property_category",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("property_category");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("sale");
                                                            }});
                                                        }
                                                    });
                                        }});
                                put(IFieldConfigPersistence.SHARE_FIELDS,
                                        new HashMap<String, FieldConfig>() {{
                                            put("field",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("field");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("value3");
                                                            }});
                                                        }
                                                    });
                                            put("property_category",
                                                    new FieldConfig() {
                                                        {
                                                            setFieldName("property_category");
                                                            setProcessingType(ProcessingType.MONITOR);
                                                            setValues(new ArrayList<String>() {{
                                                                add("sharing");
                                                            }});
                                                        }
                                                    });
                                        }});

                            }

                        }
                }
        };
    }

    @Test
    @UseDataProvider("data")
    public void testPropertiesAreAddedCorrectly(String testName, PropertyInfo[] propertyInfos, Map<String, Map<String, FieldConfig>> expectedResults) {

        DataConsistencyPropertyInfoHandler handler = new DataConsistencyPropertyInfoHandler(new IFieldConfigPersistence() {
            @Override
            public Map<String, FieldConfig> loadFields(String fieldSet) {
                return new HashMap<>();
            }

            @Override
            public void saveFields(String fieldSet, Map<String, FieldConfig> actualFieldConfigs) {

                Map<String, FieldConfig> expectedFieldConfigs = expectedResults.get(fieldSet);

                Assert.assertNotNull(expectedFieldConfigs);
                Assert.assertNotNull(actualFieldConfigs);

                Assert.assertEquals("Correct number of fields", expectedFieldConfigs.size(), actualFieldConfigs.size());

                for (String fieldName : expectedFieldConfigs.keySet()) {

                    Assert.assertTrue(actualFieldConfigs.containsKey(fieldName));

                    FieldConfig expectedFieldConfig = expectedFieldConfigs.get(fieldName);
                    FieldConfig actualFieldConfig = actualFieldConfigs.get(fieldName);

                    Assert.assertEquals(expectedFieldConfig.getFieldName(),
                            actualFieldConfig.getFieldName());

                    Assert.assertEquals(expectedFieldConfig.getValues(),
                            actualFieldConfig.getValues());

                }

            }
        });
        handler.start();

        for (PropertyInfo propertyInfo : propertyInfos) {
            handler.handle(propertyInfo);
        }

        // This will cause the method save fields to be called which will make the correct assertions;
        handler.end();
    }

}
