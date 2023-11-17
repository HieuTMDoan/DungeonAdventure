package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class NPCTile extends Tile {


    /**
     * The dungeon character occupying this tile.
     */
    private final DungeonCharacter myDungeonCharacter;


    public NPCTile(final DungeonCharacter theDungeonCharacter) {
        super(theDungeonCharacter.getDisplayChar(), false);
        this.myDungeonCharacter = theDungeonCharacter;
    }

    @Override
    public String getDescription() {
        return myDungeonCharacter.getDescription();
    }

}


