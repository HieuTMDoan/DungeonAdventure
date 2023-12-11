package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class NPCTile extends Tile {


    /**
     * The dungeon character occupying this tile.
     */
    private DungeonCharacter myDungeonCharacter;


    public NPCTile(final DungeonCharacter theDungeonCharacter) {
        super(theDungeonCharacter.getDisplayChar(), false);
        this.myDungeonCharacter = theDungeonCharacter;
    }

    public NPCTile() {

    }

    @Override
    public char getDisplayChar() {
        return this.myDungeonCharacter.getHealth() > 0
                ? myDungeonCharacter.getDisplayChar()
                : TileChars.Room.EMPTY;
    }

    @Override
    public String getTileColor() {
        return myDungeonCharacter.getTileColor();
    }

    @Override
    public boolean isTraversable() {
        return this.myDungeonCharacter.getHealth() <= 0;
    }

    @Override
    public String getDescription() {
        return myDungeonCharacter.getDescription();
    }

    public DungeonCharacter getNPC() {
        return this.myDungeonCharacter;
    }

}


