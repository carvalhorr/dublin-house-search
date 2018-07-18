package daft.filter.geographical;

import org.junit.Assert;
import org.junit.Test;

public class GeographicalPointTests {

    @Test
    public void testCreatePointUsingDoubleValuesInConstructor() {

        GeographicalPoint point = new GeographicalPoint(53.350730, -6.283515);

        Assert.assertEquals(53.350730, point.getLatitude(), 0.000001);
        Assert.assertEquals(-6.283515, point.getLongitude(), 0.000001);

    }

    @Test
    public void testCreatePointUsingStringValuesInConstructor() {

        GeographicalPoint point = new GeographicalPoint("53.350730", "-6.283515");

        Assert.assertEquals(53.350730, point.getLatitude(), 0.000001);
        Assert.assertEquals(-6.283515, point.getLongitude(), 0.000001);

    }

}
