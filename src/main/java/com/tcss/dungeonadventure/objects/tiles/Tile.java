package com.tcss.dungeonadventure.objects.tiles;

import com.tcss.dungeonadventure.objects.VisualComponent;

import java.io.*;

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
    public void onInteract() {
        if (!isTraversable()) {
            throw new RuntimeException("Target has entered a tile that should not be traversable");
        }
    }

    /**
     * Create a new instance of the Tile with the same state.
     *
     * @return A new instance of the Tile with the same state.
     */
    public Tile copy() {
        return new Tile(super.getDisplayChar(), this.myDefaultTraversable);
    }

    @Override
    public String toString() {
        return String.valueOf(super.getDisplayChar());
    }




}


