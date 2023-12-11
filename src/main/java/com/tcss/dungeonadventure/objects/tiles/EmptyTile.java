package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;

import java.io.Serial;


public class EmptyTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;


    public EmptyTile() {
        super(TileChars.Room.EMPTY, true);
    }

}
