package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;
import java.io.Serial;

/**
 * A tile that represents an unoccupied space in the room.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class EmptyTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new EmptyTile.
     */
    public EmptyTile() {
        super(TileChars.Room.EMPTY, true);
    }

}
