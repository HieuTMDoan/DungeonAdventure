package objects.items;

import objects.DungeonCharacter;
import objects.TileChars;

public class PillarOfAbstraction extends Item {



    public PillarOfAbstraction() {
        super(TileChars.Items.PILLAR_OF_ABSTRACTION);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }

}
