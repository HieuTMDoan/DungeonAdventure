package objects.items;

import objects.DungeonCharacter;
import objects.TileChars;

public class PillarOfPolymorphism extends Item {


    public PillarOfPolymorphism() {
        super(TileChars.Items.PILLAR_OF_POLYMORPHISM);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Do nothing
    }
}
