package daft;

import data.PropertyInfo;
import org.junit.Test;

public class PropertyInfoExtractorTests {


    @Test
    public void testNonExistingProperty() {
        PropertyInfoExtractor extractor = new PropertyInfoExtractor();
        PropertyInfo propertyInfo = extractor.extractPropertyInfo("21849103");
    }
}
