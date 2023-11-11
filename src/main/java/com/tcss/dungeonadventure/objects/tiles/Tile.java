package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.VisualComponent;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.items.Item;

/**
 * Parent class of all Tiles, which represents one square in
 * a room. Can contain a multitude of things, such as the
 * player, item, monsters, or interactables (doors, pits).
 *
 * @author Aaron Burnham
 * @author Hieu Doan
 * @author Sunny Ali
 */
public class Tile implements VisualComponent {

    /**
     * The character of the tile.
     */
    private final char myTileChar;

    /**
     * Boolean if the tile is traversable by the player.
     */
    private final boolean myDefaultTraversable;

    public Tile(final char theTileChar, final Item theDefaultTraversable) {
        this.myTileChar = theTileChar;
        this.myDefaultTraversable = theDefaultTraversable;
    }


    /**
     * @return The display character of the tile.
     */
    @Override
    public char getDisplayChar() {
        return this.myTileChar;
    }

    /**
     * @return The description of the tile.
     */
    @Override
    public String getDescription() {
        return "Tile: " + getClass().getSimpleName() + "\nTraversable: " + myDefaultTraversable;
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

    @Override
    public String toString() {
        return String.valueOf(this.myTileChar);
    }


}
