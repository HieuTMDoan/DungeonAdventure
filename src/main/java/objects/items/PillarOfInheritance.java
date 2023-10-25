package objects.items;

import objects.DungeonCharacter;
import objects.TileChars;

public class PillarOfInheritance extends Item {

    public PillarOfInheritance() {
        super(TileChars.Items.PILLAR_OF_INHERITANCE);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }


}
