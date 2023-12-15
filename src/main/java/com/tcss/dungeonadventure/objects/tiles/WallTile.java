package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;
import java.io.Serial;

/**
 * An impassable tile to represent terrain or walls.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class WallTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new WallTile.
     */
    public WallTile() {
        super(TileChars.Room.WALL, false);
    }



}
