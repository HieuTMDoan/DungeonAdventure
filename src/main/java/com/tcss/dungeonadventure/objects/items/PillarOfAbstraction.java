package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class PillarOfAbstraction extends Item {



    public PillarOfAbstraction() {
        super(TileChars.Items.PILLAR_OF_ABSTRACTION);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }

}
