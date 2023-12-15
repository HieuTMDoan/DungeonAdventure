package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


/**
 * One of the key items needed to beat the game.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class PillarOfAbstraction extends Item {

    /**
     * Constructs a new Pillar of Abstraction.
     */
    public PillarOfAbstraction() {
        super(TileChars.Items.PILLAR_OF_ABSTRACTION, ItemTypes.PILLAR);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }

    @Override
    public String getDescription() {
        return "Key item to unlocking the exit.";
    }


    @Override
    public String getTileColor() {
        return "purple";
    }




}
