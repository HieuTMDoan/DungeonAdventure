package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.objects.tiles.*;

import java.awt.*;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    public void generateRandomTileSetHasEntrance() {
        final Tile[][] tiles
                = Room.generateRandomTileSet(true, false, null);

        assertTrue(Arrays.stream(tiles).flatMap(Arrays::stream).anyMatch(t -> t.getDisplayChar() == TileChars.Room.ENTRANCE));
    }

    @Test
    public void generateRandomTileSetHasExit() {
        final Tile[][] tiles
                = Room.generateRandomTileSet(false, true, null);

        assertTrue(Arrays.stream(tiles).flatMap(Arrays::stream).anyMatch(t -> t.getDisplayChar() == TileChars.Room.EXIT));
    }

    @Test
    public void generateRandomTileSetBothEntranceExitException() {
        Object e = null;
        try {
            Room.generateRandomTileSet(true, true, null);
        } catch (final IllegalArgumentException ex) {
            e = ex;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }

    @Test
    public void addExtraWalls() {
        final Room room = new Room(false, false, null);

        final long wallCountBeforeMethod =
                Arrays.stream(room.getRoomTiles()).flatMap(Arrays::stream).filter(t -> t instanceof WallTile).count();

        Room.addExtraWalls(room);

        final long wallCountAfterMethod =
                Arrays.stream(room.getRoomTiles()).flatMap(Arrays::stream).filter(t -> t instanceof WallTile).count();

        assertTrue(wallCountBeforeMethod < wallCountAfterMethod);
    }

    @Test
    public void addDoorToWall() {
        final Room room1 = new Room(false, false, null);
        final Room room2 = new Room(false, false, null);

        room1.addDoorToWall(Directions.Cardinal.SOUTH, room2);
        room2.addDoorToWall(Directions.Cardinal.NORTH, room1);

        boolean foundFromRoom1 = false;
        boolean foundFromRoom2 = false;
        for (final Tile t : room1.getRoomTiles()[room1.getRoomHeight() - 1]) {
            if (t instanceof DoorTile) {
                foundFromRoom1 = true;
                break;
            }
        }

        for (final Tile t : room2.getRoomTiles()[0]) {
            if (t instanceof DoorTile) {
                foundFromRoom2 = true;
                break;
            }
        }

        assertTrue(foundFromRoom1 && foundFromRoom2);
    }

    @Test
    public void contains() {
        final Tile[][] tiles = new Tile[][]{
                {new WallTile(), new WallTile(), new WallTile(), new WallTile()},
                {new WallTile(), new EmptyTile(), new EmptyTile(), new WallTile()},
                {new WallTile(), new PitTile(), new EmptyTile(), new WallTile()},
                {new WallTile(), new WallTile(), new WallTile(), new WallTile()}
        };

        final Room room = new Room(tiles);

        assertTrue(room.contains(TileChars.Room.PIT));
        assertFalse(room.contains(TileChars.Room.HORIZONTAL_DOOR));
    }

    @Test
    public void movePlayer() {
        final Tile[][] tiles = new Tile[][]{
                {new EmptyTile(), new EmptyTile(), new EmptyTile()},
                {new EmptyTile(), new EmptyTile(), new EmptyTile()},
                {new EmptyTile(), new EmptyTile(), new EmptyTile()}
        };

        final Room room = new Room(tiles);

        for (final Directions.Cardinal direction : Directions.Cardinal.values()) {
            final Point point = new Point(1, 1);
            room.setPlayerLocation(point);
            room.movePlayer(direction);
            point.translate(direction.getXOffset(), direction.getYOffset());

            assertEquals(point, new Point(room.getPlayerXPosition(), room.getPlayerYPosition()));

        }


    }

    @Test
    public void findDoorOnWall() {
        final Room room1 = new Room(false, false, null);
        final Room room2 = new Room(false, false, null);

        room1.addDoorToWall(Directions.Cardinal.SOUTH, room2);
        room2.addDoorToWall(Directions.Cardinal.NORTH, room1);

        assertNotNull(room1.findDoorOnWall(Directions.Cardinal.SOUTH));
        assertNotNull(room2.findDoorOnWall(Directions.Cardinal.NORTH));
    }

    @Test
    public void setPlayerLocation() {
        final Room room = new Room(false, false, null);
        final Point p = new Point(1, 1);
        room.setPlayerLocation(new Point(1, 1));
        assertEquals(p, new Point(room.getPlayerXPosition(), room.getPlayerYPosition()));
    }


    @Test
    public void getDungeonLocation() {
        final Room room = new Room(false, false, null);
        final Point p = new Point(1, 1);
        room.setDungeonLocation(p);

        assertEquals(p, room.getDungeonLocation());
    }

    @Test
    public void setDungeonLocation() {
        final Room room = new Room(false, false, null);
        final Point p = new Point(1, 1);
        room.setDungeonLocation(p);

        assertEquals(p, room.getDungeonLocation());
    }

    @Test
    public void getAdjacentRoomByDirection() {
        fail();

    }

    @Test
    public void testGetAdjacentRoomByDirection() {
        fail();

    }

    @Test
    public void isEntranceRoom() {
        final Room room = new Room(true, false, null);
        assertTrue(room.isEntranceRoom());
    }

    @Test
    public void isEntranceRoomNonEntrance() {
        final Room room = new Room(false, false, null);
        assertFalse(room.isEntranceRoom());
    }

    @Test
    public void isExitRoom() {
        final Room room = new Room(false, true, null);
        assertTrue(room.isExitRoom());
    }

    @Test
    public void isExitRoomNonExit() {
        final Room room = new Room(false, false, null);
        assertFalse(room.isExitRoom());
    }

    @Test
    public void getRoomWidth() {
        final int width = Helper.getRandomIntBetween(3, 6);
        final int height = Helper.getRandomIntBetween(3, 6);

        final Tile[][] tiles = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new EmptyTile();
            }
        }

        final Room room = new Room(tiles);

        assertEquals(width, (int) room.getRoomWidth());
    }

    @Test
    public void getRoomHeight() {
        final int width = Helper.getRandomIntBetween(3, 6);
        final int height = Helper.getRandomIntBetween(3, 6);

        final Tile[][] tiles = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new EmptyTile();
            }
        }

        final Room room = new Room(tiles);

        assertEquals(height, (int) room.getRoomHeight());
    }

    @Test
    public void deepCopyRoomData() {
        fail();

    }

    @Test
    public void createMemento() {
        fail();

    }

    @Test
    public void saveToMemento() {
        fail();

    }

    @Test
    public void restoreFromMemento() {
        fail();

    }

    @Test
    public void getPlayerXPosition() {
        final Room room = new Room(false, false, null);
        room.setPlayerLocation(new Point(1, 1));

        assertEquals(1, room.getPlayerXPosition());

    }

    @Test
    public void getPlayerYPosition() {
        final Room room = new Room(false, false, null);
        room.setPlayerLocation(new Point(1, 1));

        assertEquals(1, room.getPlayerYPosition());

    }

    @Test
    public void getPillar() {
        for (final Class<?> pillar : Helper.getPillarList()) {
            final Room room = new Room(false, false, pillar);
            assertEquals(pillar, room.getPillar().getClass());
        }
    }

    @Test
    public void getRoomTiles() {
        final Room room = new Room(false, false, null);
        assertNotNull(room.getRoomTiles());
    }

    @Test
    public void testToString() {

        final Tile[][] tiles = new Tile[][]{
                {new WallTile(), new WallTile(), new WallTile(), new WallTile()},
                {new WallTile(), new EmptyTile(), new EmptyTile(), new WallTile()},
                {new WallTile(), new PitTile(), new EmptyTile(), new WallTile()},
                {new WallTile(), new WallTile(), new WallTile(), new WallTile()}
        };

        final Room room = new Room(tiles);

        final StringBuilder sb = new StringBuilder();
        for (final Tile[] row : tiles) {
            String prefix = "";
            for (final Tile tile : row) {
                sb.append(prefix).append(tile.getDisplayChar());
                prefix = " ";
            }
            sb.append("\n");
        }

        assertEquals(room.toString(), sb.toString());


    }
}