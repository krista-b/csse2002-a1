package bms.sensors;

/**
 * A sensor that measures ambient temperature in a room.
 */
public class TemperatureSensor extends TimedSensor implements HazardSensor {

    /**
     * Creates a new temperature sensor with the given sensor readings and
     * update frequency.
     *
     * @param sensorReadings a non-empty array of sensor readings
     */
    public TemperatureSensor(int[] sensorReadings) {
        super(sensorReadings, 1);
    }

    @Override
    public int getHazardLevel() {
        if (getCurrentReading() >= 68) {
            return 100;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", type=TemperatureSensor";
    }
}
