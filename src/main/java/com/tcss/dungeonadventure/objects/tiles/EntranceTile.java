package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;
import java.io.Serial;

/**
 * A tile to represent the spawn point of the player.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class EntranceTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new EntranceTile.
     */
    public EntranceTile() {
        super(TileChars.Room.ENTRANCE, true);
    }

    @Override
    public String getTileColor() {
        return "hotpink";
    }



}
