package objects.items;

import objects.DungeonCharacter;
import objects.TileChars;

public class VisionPotion extends Item {

    public VisionPotion() {
        super(TileChars.Items.VISION_POTION);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // TODO: allow player to see surrounding rooms
    }

}
