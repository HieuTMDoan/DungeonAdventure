package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.Directions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

public class DungeonTest {


    @Test
    public void getCurrentRoom() {
        final Dungeon dungeon = new Dungeon();

        final Room room = dungeon.getRoomAt(0, 0);
        dungeon.loadPlayerTo(room, new Point(0, 0));

        assertSame(room, dungeon.getCurrentRoom());
    }

    @Test
    public void getStartingRoom() {
        final Dungeon dungeon = new Dungeon();

        Room startingRoom = null;
        for (final Room[] row : dungeon.getMaze()) {
            for (final Room room : row) {
                if (room.isEntranceRoom()) {
                    startingRoom = room;
                    break;
                }
            }
        }

        if (startingRoom == null) {
            fail();
        }

        assertSame(startingRoom, dungeon.getStartingRoom());
    }

    @Test
    public void loadPlayerTo() {
        final Dungeon dungeon = new Dungeon();

        final Room room = dungeon.getRoomAt(0, 0);
        dungeon.loadPlayerTo(room, new Point(0, 0));

        assertNotNull(room.getPlayerXPosition());
    }

    @Test
    public void testLoadPlayerTo() {
        final Dungeon dungeon = new Dungeon();

        final Room room = dungeon.getRoomAt(0, 0);

        final Set<Directions.Cardinal> s = new HashSet<>();
        for (final Directions.Cardinal d : Directions.Cardinal.values()) {
            if (room.findDoorOnWall(d) != null) {
                s.add(d);
            }
        }

        for (final Directions.Cardinal d : s) {
            dungeon.loadPlayerTo(dungeon.getStartingRoom(), new Point(1, 1));
            dungeon.loadPlayerTo(room, d.getOpposite());
            assertNotNull(room.getPlayerXPosition());
        }

    }

    @Test
    public void getRoomAt() {
        final Dungeon dungeon = new Dungeon();

        final Room room = dungeon.getStartingRoom();

        final Point startingPoint = room.getDungeonLocation();
        assertSame(room, dungeon.getRoomAt(startingPoint.x, startingPoint.y));

    }

}