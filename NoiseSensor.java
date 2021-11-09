package bms.sensors;

/**
 * A sensor that measures the noise levels in a room.
 */
public class NoiseSensor extends TimedSensor implements HazardSensor {

    /**
     * Creates a new noise sensor with the given sensor readings and update
     * frequency.
     *
     * @param sensorReadings  array of noise sensor readings in decibels
     * @param updateFrequency indicates how often the sensor readings update,
     *                        in minutes
     */
    public NoiseSensor(int[] sensorReadings, int updateFrequency) {
        super(sensorReadings, updateFrequency);
    }

    /**
     * Calculates the relative loudness level compared to a reference of 70.0
     * decibels.
     *
     * @return relative loudness of current reading to 70dB
     */
    public double calculateRelativeLoudness() {
        return Math.pow(((getCurrentReading() - 70.0) / 10), 2);
    }

    @Override
    public int getHazardLevel() {
        return Math.min(((int) (calculateRelativeLoudness() * 100)), 100);
    }

    @Override
    public String toString() {
        return super.toString() + ", type=NoiseSensor";
    }

}
