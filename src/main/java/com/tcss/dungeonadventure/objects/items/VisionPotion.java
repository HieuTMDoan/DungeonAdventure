package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;
import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.Room;

public abstract class VisionPotion extends Item {

    public VisionPotion() {
        super(TileChars.Items.VISION_POTION);
    }
}
