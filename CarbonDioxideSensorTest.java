package bms.sensors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CarbonDioxideSensorTest {
    private CarbonDioxideSensor sensor1;
    private CarbonDioxideSensor sensor2;
    private CarbonDioxideSensor sensor3;
    private CarbonDioxideSensor sensor4;
    private CarbonDioxideSensor sensorIllegal;

    @Before
    public void setUp() {
        sensor1 = new CarbonDioxideSensor(new int[]{0, 100}, 5, 700, 150);
        sensor2 = new CarbonDioxideSensor(new int[]{1000}, 5, 700, 150);
        sensor3 = new CarbonDioxideSensor(new int[]{2000}, 5, 700, 150);
        sensor4 = new CarbonDioxideSensor(new int[]{5000}, 5, 700, 150);
    }

    @Test( expected = IllegalArgumentException.class )
    public void updateFrequencyIllegalNull() {
        sensorIllegal = new CarbonDioxideSensor(null, 5, 700, 150);
    }

    @Test( expected = IllegalArgumentException.class )
    public void updateFrequencyIllegalEmpty() {
        sensorIllegal = new CarbonDioxideSensor(new int[]{}, 5, 700, 150);
    }

    @Test( expected = IllegalArgumentException.class )
    public void updateFrequencyIllegalUpdateFreq() {
        sensorIllegal = new CarbonDioxideSensor(new int[]{0, 100}, -1, 700, 150);
        sensorIllegal = new CarbonDioxideSensor(new int[]{0, 100}, 6, 700, 150);
    }

    @Test( expected = IllegalArgumentException.class )
    public void updateFrequencyIllegalReading() {
        sensorIllegal = new CarbonDioxideSensor(new int[]{0, -1},  -1, 700,
                150);
    }

    @Test
    public void getVariationLimit() {
        Assert.assertEquals(150, sensor1.getVariationLimit());

    }

    @Test
    public void getIdealValue() {
        Assert.assertEquals(700, sensor1.getIdealValue());
    }

    @Test
    public void getHazardLevel() {
        Assert.assertEquals(0, sensor1.getHazardLevel());
        Assert.assertEquals(25, sensor2.getHazardLevel());
        Assert.assertEquals(50, sensor3.getHazardLevel());
        Assert.assertEquals(100, sensor4.getHazardLevel());
    }


    @Test
    public void testToString() {
        Assert.assertEquals("TimedSensor: freq=5, readings=0,100, " +
                "type=CarbonDioxideSensor, idealPPM=700, varLimit=150",
                sensor1.toString());
    }
}