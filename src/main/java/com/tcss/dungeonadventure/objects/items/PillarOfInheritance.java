package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


public class PillarOfInheritance extends Item {


    public PillarOfInheritance() {
        super(TileChars.Items.PILLAR_OF_INHERITANCE, ItemTypes.PILLAR);

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
