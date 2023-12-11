package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


public class PillarOfAbstraction extends Item {

    public PillarOfAbstraction() {
        super(TileChars.Items.PILLAR_OF_ABSTRACTION, ItemTypes.PILLAR);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }
    public Item copy() {
        // Create a new instance of PillarOfAbstraction
        // with the same display character and item type
        return new PillarOfAbstraction();
    }

    @Override
    public String getTileColor() {
        return "purple";
    }




}
