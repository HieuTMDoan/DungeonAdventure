package objects.tiles;

import objects.Directions;
import objects.TileChars;

public class DoorTile extends Tile {

    public DoorTile(final Directions.Axis axis) {
        super(axis == (Directions.Axis.VERTICAL)
                        ? TileChars.Room.VERTICAL_DOOR
                        : TileChars.Room.HORIZONTAL_DOOR,
                true);
    }


}
