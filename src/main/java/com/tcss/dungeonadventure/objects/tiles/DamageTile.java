package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class DamageTile extends Tile {


    public DamageTile() {
        super(TileChars.Room.PIT, true);
    }

    @Override
    public void onInteract(final DungeonCharacter theTarget) {


    }




}
