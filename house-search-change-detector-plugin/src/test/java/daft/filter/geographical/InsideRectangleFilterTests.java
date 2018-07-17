package daft.filter.geographical;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class InsideRectangleFilterTests {

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

    @Test
    public void testApplyFilterPointInsideRectable() {

        InsideRectangleFilter filter = new InsideRectangleFilter(
                new GeographicalPoint(53.350730, -6.283515),
                new GeographicalPoint(53.346176, -6.274910));

        Map<String, String> fields = new HashMap<String, String>(){
            {
                put("latitude","53.348437");
                put("longitude", "-6.278408");
            }
        };


        Assert.assertTrue("Wrong evaluation of point inside rectangle ", filter.apply(fields));

    }

    @Test
    public void testApplyFilterPointInsideRectable_ReversedPoints() {

        InsideRectangleFilter filter = new InsideRectangleFilter(
                new GeographicalPoint(53.346176, -6.274910),
                new GeographicalPoint(53.350730, -6.283515));

        Map<String, String> fields = new HashMap<String, String>(){
            {
                put("latitude","53.348437");
                put("longitude", "-6.278408");
            }
        };


        Assert.assertTrue("Wrong evaluation of point inside rectangle ", filter.apply(fields));

    }

    @Test
    public void testApplyFilterPointOutsideRectable() {

        InsideRectangleFilter filter = new InsideRectangleFilter(
                new GeographicalPoint("53.350730", "-6.283515"),
                new GeographicalPoint("53.346176", "-6.274910"));

        Map<String, String> fields = new HashMap<String, String>(){
            {
                put("latitude","53.344594");
                put("longitude", "-6.282324");
            }
        };


        Assert.assertFalse("Wrong evaluation of point outside rectangle ", filter.apply(fields));

    }

    @Test
    public void testApplyFilterPointOutsideRectable_ReversedPoints() {

        InsideRectangleFilter filter = new InsideRectangleFilter(
                new GeographicalPoint("53.346176", "-6.274910"),
                new GeographicalPoint("53.350730", "-6.283515"));

        Map<String, String> fields = new HashMap<String, String>(){
            {
                put("latitude","53.344594");
                put("longitude", "-6.282324");
            }
        };


        Assert.assertFalse("Wrong evaluation of point outside rectangle ", filter.apply(fields));

    }


}
