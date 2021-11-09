package bms.sensors;

/**
 * A sensor that measures the number of people in a room.
 */
public class OccupancySensor extends TimedSensor implements HazardSensor {
    private int capacity;

    /**
     * Creates a new occupancy sensor with the given sensor readings, update
     * frequency and capacity.
     *
     * @param sensorReadings  a non-empty array of sensor readings
     * @param updateFrequency indicates how often the sensor readings update,
     *                        in minutes
     * @param capacity        maximum allowable number of people in the room
     */
    public OccupancySensor(int[] sensorReadings, int updateFrequency,
                           int capacity) {
        super(sensorReadings, updateFrequency);
        this.capacity = capacity;
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the capacity of this occupancy sensor.
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getHazardLevel() {
        if (getCurrentReading() >= getCapacity()) {
            return 100;
        } else {
            return (getCurrentReading() / getCapacity()) * 100;
        }
    }

    @Override
    public String toString() {
        return super.toString() +  ", type=OccupancySensor, capacity=" +
                capacity;
    }

}
