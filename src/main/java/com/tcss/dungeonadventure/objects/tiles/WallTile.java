package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;

import java.io.Serial;


public class WallTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    public WallTile() {
        super(TileChars.Room.WALL, false);
    }



}
