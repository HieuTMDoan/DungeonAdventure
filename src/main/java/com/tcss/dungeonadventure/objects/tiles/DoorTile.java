package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.TileChars;

public class DoorTile extends Tile {

    private final Directions.Cardinal myDoorDirection;

    private final Room myDestinationRoom;


    public DoorTile(final Directions.Cardinal theDoorDirection,
                    final Room theDestinationRoom) {

        super(theDoorDirection == Directions.Cardinal.NORTH
                        || theDoorDirection == Directions.Cardinal.SOUTH
                        ? TileChars.Room.VERTICAL_DOOR
                        : TileChars.Room.HORIZONTAL_DOOR,
                true);

        this.myDestinationRoom = theDestinationRoom;
        this.myDoorDirection = theDoorDirection;
    }

    @Override
    public void onInteract() {
        DungeonAdventure.getInstance().changeRoom(myDoorDirection);
        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDestinationRoom);
        // move player to new room
    }
}
