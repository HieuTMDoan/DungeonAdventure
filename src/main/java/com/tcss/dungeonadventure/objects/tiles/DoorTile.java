package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.TileChars;

public class DoorTile extends Tile {

    /**
     * The direction of the door.
     */
    private Directions.Cardinal myDoorDirection;

    /**
     * The room that the door leads to.
     */
    private Room myDestinationRoom;


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

    public DoorTile() {

    }

    @Override
    public void onInteract(final Player thePlayer) {
        DungeonAdventure.getInstance().changeRoom(myDoorDirection);
        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDestinationRoom);
    }

    @Override
    public String getTileColor() {
        return "coral";
    }
}
