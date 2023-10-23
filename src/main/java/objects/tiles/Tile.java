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
    private final boolean myTraversable;
    private final boolean myInteractable;

    public Tile(final char theTileChar, final boolean theTraversable, final boolean theInteractable) {
        this.myTileChar = theTileChar;
        this.myTraversable = theTraversable;
        this.myInteractable = theInteractable;
    }

    public char getTileChar() {
        return this.myTileChar;
    }

    /**
     * Returns if the tile can be stepped on. Can be overridden to allow
     * player to traverse over the tile during a specific state.
     */
    public boolean isTraversable() {
        return this.myTraversable;
    }

    /**
     * Returns if the tile can be interacted with. Can be overridden to allow
     * player to interact with the tile during a specific state.
     */
    public boolean isInteractable() {
        return this.myInteractable;
    }

    public void onStepOver(final DungeonCharacter target) {
        // do nothing by default
    }


}
