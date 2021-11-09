package bms.sensors;

/**
 * A sensor that measures levels of carbon dioxide (CO2) in the air, in parts
 * per million (ppm).
 */
public class CarbonDioxideSensor extends TimedSensor implements HazardSensor {
    private int idealValue;
    private int variationLimit;

    /**
     * Creates a new carbon dioxide sensor with the given sensor readings,
     * update frequency, ideal CO2 value and acceptable variation limit.
     *
     * @param sensorReadings  array of CO2 sensor readings in ppm
     * @param updateFrequency indicates how often the sensor readings update,
     *                        in minutes
     * @param idealValue      ideal CO2 value in ppm
     * @param variationLimit  acceptable range above and below ideal value
     *                        in ppm
     * @throws IllegalArgumentException if idealValue <= 0; or if
     *                                  variationLimit <= 0; or if
     *                                  (idealValue - variationLimit) < 0
     */
    public CarbonDioxideSensor(int[] sensorReadings, int updateFrequency,
                               int idealValue, int variationLimit)
            throws IllegalArgumentException {
        super(sensorReadings, updateFrequency);
        this.idealValue = idealValue;
        this.variationLimit = variationLimit;
        if (idealValue <= 0 || variationLimit <= 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the sensor's CO2 variation limit.
     *
     * @return variation limit in ppm
     */
    public int getVariationLimit() {
        return variationLimit;
    }

    /**
     * Returns the sensor's ideal CO2 value.
     *
     * @return ideal value in ppm
     */
    public int getIdealValue() {
        return idealValue;
    }

    @Override
    public int getHazardLevel() {
        if (getCurrentReading() >= 0 && getCurrentReading() <= 999) {
            return 0;
        } else if (getCurrentReading() >= 1000 &&
                getCurrentReading() <= 1999) {
            return 25;
        } else if (getCurrentReading() >= 2000 &&
                getCurrentReading() <= 4999) {
            return 50;
        } else {
            return 100;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", type=CarbonDioxideSensor, " + "idealPPM" +
                "=" + idealValue + ", varLimit=" + variationLimit;
    }
}
