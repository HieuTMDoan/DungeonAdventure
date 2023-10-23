package objects.tiles;

import objects.DungeonCharacter;

public abstract class Tile {

    protected enum TileChars {
        EMPTY(' '),
        WALL('*'),
        VERTICAL_DOOR('-'),
        HORIZONTAL_DOOR('|'),
        ENTRANCE('i'),
        EXIT('O'),
        ITEM,
        DAMAGE,
        NPC,
        ;

        private final Character myTileChar;

        /**
         * Constructor when the tile character is variable.
         */
        TileChars() {
            myTileChar = null;
        }

        /**
         * Constructor when the tile character is static.
         *
         * @param theTileChar The tile character.
         */
        TileChars(final Character theTileChar) {
            this.myTileChar = theTileChar;
        }

        public Character getTileChar() {
            return this.myTileChar;
        }


    }

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
