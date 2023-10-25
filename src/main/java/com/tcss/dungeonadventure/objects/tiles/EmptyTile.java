package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;

class EmptyTile extends Tile {


    protected EmptyTile() {
        super(TileChars.Room.EMPTY, true);
    }

}
