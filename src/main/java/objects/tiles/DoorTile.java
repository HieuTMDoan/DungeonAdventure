package objects.tiles;

import objects.Directions;

public class DoorTile extends Tile {

    public DoorTile(final Directions.Axis axis) {
        super(axis == (Directions.Axis.VERTICAL)
                        ? TileChars.VERTICAL_DOOR.getTileChar()
                        : TileChars.HORIZONTAL_DOOR.getTileChar(),
                true);
    }


}
