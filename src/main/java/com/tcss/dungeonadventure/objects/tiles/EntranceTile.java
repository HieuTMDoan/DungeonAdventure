package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;

import java.io.Serial;

public class EntranceTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntranceTile() {
        super(TileChars.Room.ENTRANCE, true);
    }

    @Override
    public String getTileColor() {
        return "hotpink";
    }



}
