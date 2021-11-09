package bms.util;

import bms.exceptions.FireDrillException;
import bms.room.RoomType;

/**
 * Denotes a class containing a routine to carry out fire drills on rooms of
 * a given type.
 */
public interface FireDrill {

    /**
     * Starts a fire drill in all rooms of the given type on the floor.
     *
     * @param roomType the type of room to carry out fire drills on; null if
     *                 fire drills are to be carried out in all rooms
     * @throws FireDrillException if there are no floors in the building, or
     *                            there are floors but no rooms in the building
     */
    void fireDrill(RoomType roomType) throws FireDrillException;
}
