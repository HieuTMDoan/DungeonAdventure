package objects.tiles;

import objects.TileChars;

class EmptyTile extends Tile {


    protected EmptyTile() {
        super(TileChars.Room.EMPTY, true);
    }

}
