package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.TileChars;
import java.io.Serial;


/**
 * A tile that will result in the change of rooms when interacted with.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class DoorTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The direction of the door.
     */
    private Directions.Cardinal myDoorDirection;

    /**
     * The room that the door leads to.
     */
    private Room myDestinationRoom;


    /**
     * Constructs a new DoorTile based on the direction the door is
     * and the room the door leads to.
     *
     * @param theDoorDirection The direction of the door.
     * @param theDestinationRoom The Room the door leads to.
     */
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

    /**
     * Empty constructor for Serialization.
     */
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
