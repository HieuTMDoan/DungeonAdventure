package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;

import java.io.Serializable;

public class WallTile extends Tile implements Serializable {

    public WallTile() {
        super(TileChars.Room.WALL, false);
    }



}
