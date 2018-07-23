package daft.consistency.data;

import java.util.LinkedList;
import java.util.List;

public class FieldConfig {

    private String fieldName;
    private ProcessingType processingType;
    private List<String> values;

    public FieldConfig() {
        values = new LinkedList<>();
        processingType = ProcessingType.MONITOR;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public ProcessingType getProcessingType() {
        return processingType;
    }

    public void setProcessingType(ProcessingType processingType) {
        this.processingType = processingType;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
