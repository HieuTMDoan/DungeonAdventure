package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class TrapDoorTile extends Tile {

    private final Directions.Axis myDoorAxis;


    public TrapDoorTile(final Directions.Axis theAxis) {
        super(theAxis == Directions.Axis.VERTICAL
                        ? TileChars.Room.VERTICAL_DOOR
                        : TileChars.Room.HORIZONTAL_DOOR,
                true);
        this.myDoorAxis = theAxis;
    }

    @Override
    public void onInteract(final DungeonCharacter theTarget) {
        // move player to new room
    }
    public Directions.Axis getDoorAxis() {
        return myDoorAxis;
    }
}

