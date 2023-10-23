package objects.tiles;

public class WallTile extends Tile {

    private final static char DISPLAY_CHAR = '*';

    public WallTile() {
        super(DISPLAY_CHAR, false, false);
    }

}
