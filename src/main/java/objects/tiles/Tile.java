package objects.tiles;

import objects.DungeonCharacter;

public abstract class Tile {

    private final char myTileChar;
    private final boolean myDefaultTraversable;

    public Tile(final char theTileChar, final boolean theDefaultTraversable) {
        this.myTileChar = theTileChar;
        this.myDefaultTraversable = theDefaultTraversable;
    }

    public char getTileChar() {
        return this.myTileChar;
    }

    /**
     * Returns if the tile can be stepped on. Can be overridden to allow
     * player to traverse over the tile during a specific state.
     */
    public boolean isTraversable() {
        return this.myDefaultTraversable;
    }

    /**
     * This method should be fired when the user steps on a tile.
     *
     * @param theTarget
     */
    public void onInteract(final DungeonCharacter theTarget) {
        if (!isTraversable()) {
            throw new RuntimeException(
                    "Target has entered a tile that should not be traversable");
        }
    }



}
