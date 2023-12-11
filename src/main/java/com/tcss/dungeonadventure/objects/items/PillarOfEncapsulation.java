package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

import java.io.Serial;
import java.io.Serializable;

public class PillarOfEncapsulation extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public PillarOfEncapsulation() {
        super(TileChars.Items.PILLAR_OF_ENCAPSULATION, ItemTypes.PILLAR);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }

    @Override
    public Item copy() {
        // Create a new instance of PillarOfEncapsulation
        // with the same display character and item type
        return new PillarOfEncapsulation();
    }

    @Override
    public String getTileColor() {
        return "purple";
    }
}
