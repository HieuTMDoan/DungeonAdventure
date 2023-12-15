package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;
import java.io.Serial;


/**
 * A tile to represent other {@link DungeonCharacter}, such as Monsters.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */

public class NPCTile extends Tile {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The dungeon character occupying this tile.
     */
    private DungeonCharacter myDungeonCharacter;

    /**
     * Constructs a new NPCTile based on the specified DungeonCharacter.
     *
     * @param theDungeonCharacter The DungeonCharacter on the tile.
     */
    public NPCTile(final DungeonCharacter theDungeonCharacter) {
        super(theDungeonCharacter.getDisplayChar(), false);
        this.myDungeonCharacter = theDungeonCharacter;
    }


    /**
     * Empty tile for Serialization.
     */
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


