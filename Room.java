package bms.room;

import bms.exceptions.DuplicateSensorException;
import bms.sensors.Sensor;

import java.util.*;
import java.lang.*;

/**
 * Represents a room on a floor of a building.
 */
public class Room {
    private int roomNumber;
    private RoomType type;
    private double area;
    private List<Sensor> sensors;
    private boolean fireDrillOn;

    /**
     * Creates a room with the given room number.
     *
     * @param roomNumber the unique room number of the room on this floor
     * @param type       the type of room
     * @param area       the area of the room in square metres
     */
    public Room(int roomNumber, RoomType type, double area) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.area = area;
        sensors = new ArrayList<>();
        fireDrillOn = false;
    }

    /**
     * Returns room number of the room.
     *
     * @return the room number on the floor
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns area of the room.
     *
     * @return the room area in square metres
     */
    public double getArea() {
        return area;
    }

    /**
     * Returns the minimum area for all rooms.
     *
     * @return the minimum room area in square metres
     */
    public static int getMinArea() {
        return 5;
    }

    /**
     * Returns the type of the room.
     *
     * @return the room type
     */
    public RoomType getType() {
        return type;
    }

    /**
     * Returns whether there is currently a fire drill in progress.
     *
     * @return current status of fire drill
     */
    public boolean fireDrillOngoing() {
        return fireDrillOn;
    }

    /**
     * Returns the list of sensors in the room.
     *
     * @return list of all sensors in alphabetical order of class name
     */
    public List<Sensor> getSensors() {
        List<Sensor> sortedSensors = new ArrayList<>(sensors);
        sortedSensors.sort(Comparator.comparing((Sensor sensor) -> sensor.getClass().getSimpleName()));
        return sortedSensors;
    }

    /**
     * Change the status of the fire drill to the given value.
     *
     * @param fireDrill whether there is a fire drill ongoing
     */
    public void setFireDrill​(boolean fireDrill) {
        fireDrillOn = fireDrill;
    }

    /**
     * Return the given type of sensor if there is one in the list of sensors;
     * return null otherwise.
     *
     * @param sensorType the type of sensor which matches the class name
     *                   returned by the getSimpleName() method
     * @return the sensor in this room of the given type; null if none found
     */
    public Sensor getSensor​(String sensorType) {
        for (Sensor sensor : sensors) {
            if (sensor.getClass().getSimpleName().equals(sensorType)) {
                return sensor;
            }
        }
        return null;
    }

    /**
     * Adds a sensor to the room if a sensor of the same type is not already
     * in the room.
     *
     * @param sensor the sensor to add to the room
     * @throws DuplicateSensorException if the sensor to add is of the same
     *                                  type as a sensor already in this room
     */
    public void addSensor​(Sensor sensor) throws DuplicateSensorException {
        if (getSensor​(sensor.getClass().getSimpleName()) != null) {
            throw new DuplicateSensorException();
        } else {
            sensors.add(sensor);
        }
    }

    @Override
    public String toString() {
        return "Room #" + roomNumber + ": type=" + type + ", area=" +
                String.format("%" + ".2f", area) + "m^2, " + "sensors=" +
                sensors.size();
    }
}
