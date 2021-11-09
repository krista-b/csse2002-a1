package bms.floor;

import bms.exceptions.*;
import bms.room.*;
import bms.util.FireDrill;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a floor of a building.
 */
public class Floor implements FireDrill {
    private int floorNumber;
    private double width;
    private double length;
    private List<Room> rooms;

    /**
     * Creates a new floor with the given floor number.
     *
     * @param floorNumber a unique floor number, corresponds to how many
     *                    floors above ground floor (inclusive)
     * @param width       the width of the floor in metres
     * @param length      the length of the floor in metres
     */
    public Floor(int floorNumber, double width, double length) {
        this.floorNumber = floorNumber;
        this.width = width;
        this.length = length;
        rooms = new ArrayList<>();
    }

    /**
     * Returns the floor number of this floor.
     *
     * @return floor number
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Returns the minimum width for all floors.
     *
     * @return 5
     */
    public static int getMinWidth() {
        return 5;
    }

    /**
     * Returns the minimum length for all floors.
     *
     * @return 5
     */
    public static int getMinLength() {
        return 5;
    }

    /**
     * Returns a new list containing all the rooms on this floor.
     *
     * @return new list containing all rooms on the floor
     */
    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    /**
     * Returns width of the floor.
     *
     * @return floor width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns length of the floor.
     *
     * @return floor length
     */
    public double getLength() {
        return length;
    }

    /**
     * Search for the room with the specified room number and if it exists,
     * returns it, otherwise returns null.
     *
     * @return room with the given number if found; null if not found
     */
    public Room getRoomByNumber​(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    /**
     * Calculates the area of the floor in square metres.
     *
     * @return area of floor in square metres
     */
    public double calculateArea() {
        return getWidth() * getLength();
    }

    /**
     * Calculates the area of the floor which is currently occupied by all
     * the rooms on the floor.
     *
     * @return area of floor that is currently occupied, in square metres
     */
    public float occupiedArea() {
        double total = 0;
        for (Room room : rooms) {
            total += room.getArea();
        }
        return (float) total;
    }

    /**
     * Adds a room to the floor.
     *
     * @param newRoom object representing the new room
     * @throws IllegalArgumentException   if area is less than Room.getMinArea()
     * @throws DuplicateRoomException     if the room number on this floor is
     *                                    already taken
     * @throws InsufficientSpaceException if there is insufficient space
     *                                    available on the floor to be able to
     *                                    add the room
     */
    public void addRoom​(Room newRoom) throws DuplicateRoomException,
                                              InsufficientSpaceException {
        if (newRoom.getArea() < Room.getMinArea()) {
            // if the room is too small
            throw new IllegalArgumentException();
        } else if (getRoomByNumber​(newRoom.getRoomNumber()) != null) {
            // if the room already exists
            throw new DuplicateRoomException();
        } else if ((calculateArea() - occupiedArea()) < newRoom.getArea()) {
            // if there is not enough space left on the floor to fit the room
            throw new InsufficientSpaceException();
        } else {
            rooms.add(newRoom);
        }
    }

    @Override
    public void fireDrill(RoomType roomType) {
        for (Room room : rooms) {
            if (roomType == null) {
                room.setFireDrill​(true);
            } else if (room.getType() == roomType) {
                room.setFireDrill​(true);
            }
        }
    }

    /**
     * Cancels any ongoing fire drill in rooms on the floor.
     */
    public void cancelFireDrill() {
        for (Room room : rooms) {
            if (room.fireDrillOngoing()) {
                room.setFireDrill​(false);
            } else {
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Floor #" + floorNumber + ": width=" +
                String.format("%.2f", width) + "m, " + "length" + "=" +
                String.format("%.2f", length) + "m, rooms=" + rooms.size();
    }
}
