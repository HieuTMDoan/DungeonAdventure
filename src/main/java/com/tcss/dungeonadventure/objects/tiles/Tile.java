package com.tcss.dungeonadventure.objects.tiles;

import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.VisualComponent;
import java.io.Serial;
import java.io.Serializable;


/**
 * An abstract class of an individual component of the layout of a Room.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class Tile extends VisualComponent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Boolean if the tile is traversable by the player.
     */
    private final boolean myDefaultTraversable;

    public Tile(final char theTileChar, final boolean theDefaultTraversable) {
        super(theTileChar);
        this.myDefaultTraversable = theDefaultTraversable;
    }

    /**
     * No-argument constructor for deserialization.
     */
    public Tile() {
        this(' ', true);
    }

    /**
     * @return The description of the tile.
     */
    @Override
    public String getDescription() {
        return "Tile: " + getClass().getSimpleName()
                + "\nTraversable: " + myDefaultTraversable;
    }

    /**
     * Returns if the tile can be stepped on. Can be overridden to allow
     * the player to traverse over the tile during a specific state.
     */
    public boolean isTraversable() {
        return this.myDefaultTraversable;
    }

    /**
     * This method should be fired when the user steps on a tile.
     */
    public void onInteract(final Player thePlayer) {
        if (!isTraversable()) {
            throw new RuntimeException(
                    "Target has entered a tile that should not be traversable");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(super.getDisplayChar());
    }




}


