package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.TileChars;

public class EntranceTile extends Tile {


    public EntranceTile() {
        super(TileChars.Room.ENTRANCE, true);
    }

    @Override
    public String getTileColor() {
        return "hotpink";
    }



}
