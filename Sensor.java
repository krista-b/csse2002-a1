package bms.sensors;


/**
 * Represents a device that is used to monitor and report readings of some
 * observed variable at a location.
 */
public interface Sensor {

    /**
     * Returns the currrent sensor reading.
     *
     * @return the current sensor reading
     */
    int getCurrentReading();
}
