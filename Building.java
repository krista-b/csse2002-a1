package bms.building;

import bms.exceptions.*;
import bms.floor.Floor;
import bms.room.*;
import bms.util.FireDrill;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a building of floors, which in turn, contain rooms.
 *
 * A building needs to manage and keep track of all the floors within the
 * building.
 */
public class Building implements FireDrill {
    private String name;
    private List<Floor> floors;

    /**
     * Creates a new empty building with no rooms.
     *
     * @param name name of this building
     */
    public Building(String name) {
        this.name = name;
        floors = new ArrayList<>();
    }

    /**
     * Returns the name of the building.
     *
     * @return name of this building
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a new list containing all the floors in this building which if
     * altered does not affect the buildings internal list of floors.
     *
     * @return new list containing all floors in the building
     */
    public List<Floor> getFloors() {
        return new ArrayList<>(floors);
    }

    /**
     * Searches for the floor with the specified floor number and if it
     * exists, returns the floor, otherwise, returns null.
     *
     * @param floorNumber floor number of floor to search for
     * @return floor with the given number if found; null if not found
     */
    public Floor getFloorByNumber(int floorNumber) {
        for (Floor floor : floors) {
            if (floor.getFloorNumber() == floorNumber) {
                return floor;
            }
        }
        return null;
    }

    /**
     * Adds a floor to the building.
     *
     * If the given arguments are invalid, the floor already exists, there is
     * no floor below, or the floor below does not have enough area to support
     * this floor, an exception should be thrown and no action should be taken.
     *
     * @param newFloor object representing the new floor
     * @throws IllegalArgumentException if floor number is <= 0, width <
     *                                  Floor.getMinWidth(), or length <
     *                                  Floor.getMinLength()
     * @throws DuplicateFloorException  if a floor at this level already
     *                                  exists in the building
     * @throws NoFloorBelowException    if this is at level 2 or above and
     *                                  there is no floor below to support this
     *                                  new floor
     * @throws FloorTooSmallException   if this is at level 2 or above and the
     *                                  floor below is not big enough to
     *                                  support this new floor
     */
    public void addFloor(Floor newFloor) throws IllegalArgumentException,
                                                DuplicateFloorException,
                                                NoFloorBelowException,
                                                FloorTooSmallException {
        if (newFloor.getFloorNumber() == 0 ||
                newFloor.getWidth() < Floor.getMinWidth() ||
                newFloor.getLength() < Floor.getMinLength()) {
            // if the floorNumber is 0 or too small
            throw new IllegalArgumentException();
        } else if (newFloor.getFloorNumber() != 1 &&
                getFloorByNumber((newFloor.getFloorNumber() - 1)) == null) {
            // if there is a floor below (not for floor 1)
            throw new NoFloorBelowException();
        } else if (floors.contains(newFloor)) {
            // if the floor already exits
            throw new DuplicateFloorException();
        } else if (newFloor.getFloorNumber() > 1 &&
                (getFloorByNumber((newFloor.getFloorNumber() - 1)))
                        .calculateArea() < newFloor.calculateArea()) {
            // if the floor below (if one exits) is large enough to support the
            // new floor
            throw new FloorTooSmallException();
        } else {
            floors.add(newFloor);
        }
    }

    @Override
    public void fireDrill(RoomType roomType) throws FireDrillException {
        if (floors.isEmpty()) {
            throw new FireDrillException();
        } else {
            for (Floor floor : floors) {
                if (floor.getRooms().isEmpty()) {
                    throw new FireDrillException();
                } else {
                    floor.fireDrill(roomType);
                }
            }
        }
    }

    /**
     * Cancels any ongoing fire drill in the building.
     */
    public void cancelFireDrill() {
        for (Floor floor : floors) {
            floor.cancelFireDrill();
        }
    }

    @Override
    public String toString() {
        return "Building: name=" + name + ", floors=" + floors.size();
    }
}
