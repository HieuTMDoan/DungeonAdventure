package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class VisionPotion extends Item {

    public VisionPotion() {
        super(TileChars.Items.VISION_POTION, ItemTypes.CONSUMABLE);
    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // TODO: allow player to see surrounding rooms
    }
}
