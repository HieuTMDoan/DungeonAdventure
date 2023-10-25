package objects.items;

import objects.DungeonCharacter;
import objects.TileChars;

public class PillarOfEncapsulation extends Item {

    public PillarOfEncapsulation() {
        super(TileChars.Items.PILLAR_OF_ENCAPSULATION);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }


}
