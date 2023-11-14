package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class DoorTile extends Tile {

    private final Directions.Axis myDoorAxis;

//    private final Room myDestinationRoom;


    public DoorTile(final Directions.Axis theAxis) {
        super(theAxis == Directions.Axis.VERTICAL
                        ? TileChars.Room.VERTICAL_DOOR
                        : TileChars.Room.HORIZONTAL_DOOR,
                true);

//        this.myDestinationRoom = theDestinationRoom;

        this.myDoorAxis = theAxis;
    }

    @Override
    public void onInteract() {
//        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDestinationRoom);
        // move player to new room
    }
    public Directions.Axis getDoorAxis() {
        return myDoorAxis;
    }
}
