package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisionPotion extends Item {

    public VisionPotion() {
        super(TileChars.Items.VISION_POTION, ItemTypes.CONSUMABLE);
    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        final Room[][] newDiscoveredRooms = DungeonAdventure.getInstance().getDiscoveredRooms();
        final Room currentRoom = DungeonAdventure.getInstance().getDungeon().getCurrentRoom();

        addsAllAdjacentRooms(newDiscoveredRooms, currentRoom);
        DungeonAdventure.getInstance().setDiscoveredRooms(newDiscoveredRooms);
    }

    /**
     * Adds all the rooms surrounding the current room
     * to the list of current discovered rooms.
     *
     * @param theDiscoveredRooms the current discovered rooms.
     * @param theCurrentRoom the current room.
     */
    private static void addsAllAdjacentRooms(final Room[][] theDiscoveredRooms, final Room theCurrentRoom) {
        // Adds adjacent rooms by the cardinal directions
        for (Directions.Cardinal dir : Directions.Cardinal.values()) {
            try {
                final Room adjacentRoomByDirection = theCurrentRoom.getAdjacentRoomByDirection(dir);
                final int visibleRoomRow = adjacentRoomByDirection.getDungeonLocation().y;
                final int visibleRoomCol = adjacentRoomByDirection.getDungeonLocation().x;

                theDiscoveredRooms[visibleRoomRow][visibleRoomCol] = adjacentRoomByDirection;
            } catch (final ArrayIndexOutOfBoundsException ignored) { }
        }

        // Adds adjacent rooms by the diagonal directions
        for (Directions.Diagonal dir : Directions.Diagonal.values()) {
            try {
                final Room adjacentRoomByDirection = theCurrentRoom.getAdjacentRoomByDirection(dir);
                final int visibleRoomRow = adjacentRoomByDirection.getDungeonLocation().y;
                final int visibleRoomCol = adjacentRoomByDirection.getDungeonLocation().x;

                theDiscoveredRooms[visibleRoomRow][visibleRoomCol] = adjacentRoomByDirection;
            } catch (final ArrayIndexOutOfBoundsException ignored) { }
        }
    }

    @Override
    public Item copy() {
        // Create a new instance of VisionPotion with the same display character and item type
        return new VisionPotion();
    }
}
