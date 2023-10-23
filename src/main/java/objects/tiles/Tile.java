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

    public void onStepOver(final DungeonCharacter target) {
        // do nothing by default
    }

    public void onInteract() {
        // do nothing by default
    }


}
