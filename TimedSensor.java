package bms.sensors;

import bms.util.TimedItem;
import bms.util.TimedItemManager;
import java.util.Arrays;

/**
 * An abstract class to represent a sensor that iterates through observed
 * values on a timer.
 */
public abstract class TimedSensor implements TimedItem, Sensor {
    private int[] sensorReadings;
    private int updateFrequency;
    private int secondsElapsed;
    private int minutesElapsed;
    private int currentElement;

    /**
     * Creates a new timed sensor, using the provided list of sensor
     * readings. These represent "raw" data values, and have different
     * meanings depending on the concrete sensor class used.
     *
     * @param sensorReadings  a non-empty array of sensor readings
     * @param updateFrequency indicates how often the sensor readings updates,
     *                        in minutes
     * @throws IllegalArgumentException if updateFrequency is < 1 or > 5; or
     *                                  if sensorReadings is null; if
     *                                  sensorReadings is empty; or if any
     *                                  value in sensorReadings is less than
     *                                  zero
     */
    public TimedSensor(int[] sensorReadings, int updateFrequency)
            throws IllegalArgumentException {
        this.sensorReadings = sensorReadings;
        this.updateFrequency = updateFrequency;
        secondsElapsed = 0;
        minutesElapsed = 0;
        currentElement = 0;
        TimedItemManager.getInstance().registerTimedItem(this);

        if (updateFrequency < 1 || updateFrequency > 5 ||
                sensorReadings == null || sensorReadings.length == 0) {
            throw new IllegalArgumentException();
        }

        for (int reading : sensorReadings) {
            if (reading < 0) {
                throw new IllegalArgumentException();
            }
        }

    }

    @Override
    public int getCurrentReading() {
        return sensorReadings[currentElement];
    }

    /**
     * Returns the number of minutes that have elapsed since the sensor was
     * instantiated. Should return 0 immediately after the constructor is
     * called.
     *
     * @return the sensor's time elapsed in minutes
     */
    public int getTimeElapsed() {
        return minutesElapsed;
    }

    /**
     * Returns the number of minutes in between updates to the current sensor
     * reading.
     *
     * @return the sensor's update frequency in minutes
     */
    public int getUpdateFrequency() {
        return updateFrequency;
    }

    @Override
    public void elapseOneMinute() {
        secondsElapsed++;

        if (secondsElapsed >= 60) {
            minutesElapsed++;
        }
        if ((updateFrequency % minutesElapsed) != 0) {
            currentElement++;
            secondsElapsed = 0;
            minutesElapsed = 0;
            if (currentElement > sensorReadings.length) {
                currentElement = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "TimedSensor: freq=" + updateFrequency + ", readings=" +
                Arrays.toString(sensorReadings).replace(", ", ",")
                        .replace("[", "")
                        .replace("]", "");
    }
}
