package bms.floor;

import bms.exceptions.DuplicateRoomException;
import bms.exceptions.InsufficientSpaceException;
import bms.room.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FloorTest {
    private Floor floor;
    private Floor floorEmpty;
    private Floor floorMany;
    private Floor floorFireDrillNull;
    private Floor floorTestAddFloor;

    private Room room1;
    private Room room2;
    private Room roomDecimal;
    private Room roomLarge;
    private Room roomSmall;
    private Room roomTestAddRoom;

    private List<Room> OnFireDrill;
    private List<Room> expected;

    @Before
    public void setUp() throws Exception {
        room1 = new Room(1, RoomType.STUDY, 10);
        room2 = new Room(2, RoomType.OFFICE, 30);
        roomDecimal = new Room(4, RoomType.OFFICE, 10.2);
        roomLarge = new Room(5, RoomType.OFFICE, 60);
        roomSmall = new Room(6, RoomType.STUDY, 3);
        roomTestAddRoom = new Room(7, RoomType.LABORATORY, 10);

        floor = new Floor(1, 10, 10);
        floor.addRoom​(room1);
        floor.fireDrill(null);

        floorEmpty = new Floor(2, 10.51, 15.02);

        floorMany = new Floor(3, 10, 10);
        floorMany.addRoom​(room1);
        floorMany.addRoom​(room2);
        floorMany.addRoom​(roomDecimal);
        floorMany.fireDrill(RoomType.OFFICE);
        floorMany.fireDrill(RoomType.LABORATORY);

        floorFireDrillNull = new Floor(6, 10, 10);
        floorFireDrillNull.addRoom​(room1);
        floorFireDrillNull.addRoom​(room2);

        floorTestAddFloor = new Floor(7, 10, 10);

        OnFireDrill = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Test
    public void getFloorNumber() {
        Assert.assertEquals(1, floor.getFloorNumber());
    }

    @Test
    public void getMinWidth() {
        Assert.assertEquals(5, Floor.getMinWidth());
    }

    @Test
    public void getMinLength() {
        Assert.assertEquals(5, Floor.getMinLength());
    }

    @Test
    public void getRoomsOne() {
        expected.add(room1);
        Assert.assertEquals(expected, floor.getRooms());
    }

    @Test
    public void getRoomsMany() {
        expected.add(room1);
        expected.add(room2);
        expected.add(roomDecimal);
        Assert.assertEquals(expected, floorMany.getRooms());
    }

    @Test
    public void getRoomsEmpty() {
        Assert.assertEquals(expected, floorEmpty.getRooms());
    }

    @Test
    public void getWidth() {
        Assert.assertEquals(10, floor.getWidth(), 0);
    }

    @Test
    public void getLength() {
        Assert.assertEquals(10, floor.getLength(), 0);
    }

    @Test
    public void getRoomByNumber​() {
        Assert.assertEquals(room1, floor.getRoomByNumber​(1));
    }

    @Test
    public void getRoomByNumberNull() {
        Assert.assertNull(floor.getRoomByNumber​(2));
    }

    @Test
    public void calculateArea() {
        Assert.assertEquals(100, floor.calculateArea(), 0.001);
        Assert.assertEquals(157.8602, floorEmpty.calculateArea(), 0.001);
    }

    @Test
    public void occupiedArea() {
        Assert.assertEquals(10.0, floor.occupiedArea(), 0);
    }

    @Test
    public void occupiedAreaEmpty() {
        Assert.assertEquals(0.0, floorEmpty.occupiedArea(), 0);
    }

    @Test
    public void occupiedAreaDecimal() {
        Assert.assertEquals(50.2, floorMany.occupiedArea(), 0.001);
    }

    @Test
    public void addRoom​() throws InsufficientSpaceException,
                                  DuplicateRoomException {
        floorTestAddFloor.addRoom​(roomTestAddRoom);

        expected.add(roomTestAddRoom);
        Assert.assertEquals(expected, floorTestAddFloor.getRooms());
    }

    @Test(expected = DuplicateRoomException.class)
    public void addRoom​DuplicateRoom() throws InsufficientSpaceException,
                                               DuplicateRoomException {
        floorMany.addRoom​(room1);
    }

    @Test(expected = InsufficientSpaceException.class)
    public void addRoom​InsufficientSpace() throws InsufficientSpaceException,
                                                   DuplicateRoomException {
        floorMany.addRoom​(roomLarge);
    }

    @Test( expected = IllegalArgumentException.class )
    public void addRoom​IllegalArg() throws InsufficientSpaceException
            , DuplicateRoomException {
        floorMany.addRoom​(roomSmall);
    }

    @Test
    public void fireDrill() {
        expected.add(room2);
        expected.add(roomDecimal);

        for (Room room : floorMany.getRooms()) {
            if (room.fireDrillOngoing() && room.getType() == RoomType.OFFICE) {
                OnFireDrill.add(room);
            }
        }
        Assert.assertEquals(expected, OnFireDrill);
    }

    @Test
    public void fireDrillNoneOfType() {
        for (Room room : floorMany.getRooms()) {
            if (room.fireDrillOngoing() &&
                    room.getType() == RoomType.LABORATORY) {
                OnFireDrill.add(room);
            }
        }
        Assert.assertEquals(expected, OnFireDrill);
    }

    @Test
    public void fireDrillNull() {
        expected.add(room1);
        expected.add(room2);

        for (Room room : floorFireDrillNull.getRooms()) {
            if (room.fireDrillOngoing()) {
                OnFireDrill.add(room);
            }
        }
        Assert.assertEquals(expected, OnFireDrill);
    }

    @Test
    public void cancelFireDrill() {
        floorMany.cancelFireDrill();

        for (Room room : floorMany.getRooms()) {
            if (room.fireDrillOngoing()) {
                OnFireDrill.add(room);
            }
        }
        Assert.assertEquals(expected, OnFireDrill);
    }

    @Test
    public void testToString() {
        Assert.assertEquals("Floor #1: width=10.00m, length=10.00m, "
                                    + "rooms=1", floor.toString());
    }
}